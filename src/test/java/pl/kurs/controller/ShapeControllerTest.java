package pl.kurs.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.kurs.entity.model.Shape;
import pl.kurs.entity.model.Square;
import pl.kurs.entity.model.User;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.repository.ShapeRepository;
import pl.kurs.service.UserPermissionService;
import pl.kurs.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
class ShapeControllerTest {

    @MockBean
    private UserPermissionService userPermissionService;
    @MockBean
    private SecurityUtil securityUtil;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ShapeRepository shapeRepository;

    @BeforeEach
    public void init() {
        shapeRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void shouldSaveShape() {
        User user = new User("A", "B");

        Mockito.when(securityUtil.getUser()).thenReturn(user);
        ShapeRequest shape = new ShapeRequest("SQUARE", List.of(10.));
        String json = objectMapper.writeValueAsString(shape);

        String response = mvc.perform(post("/api/v1/shapes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Square result = objectMapper.readValue(response, Square.class);

        assertEquals("SQUARE", result.getType());
        assertEquals(100, result.getArea());
        assertEquals(40, result.getPerimeter());
        assertEquals("A", result.getCreatedBy());
        assertEquals("A", result.getLastModifiedBy());
    }

    @Test
    @SneakyThrows
    void shouldGetShapeByParameters() {
        User user = new User("A", "B");
        Mockito.when(securityUtil.getUser()).thenReturn(user);

        Shape s = new Square("SQUARE", LocalDateTime.of(2023, 1, 1, 1, 1), 1, "A", LocalDateTime.of(2023, 1, 1, 1, 1), "A", 100, 40, 10);
        shapeRepository.save(s);

        String response = mvc.perform(get("/api/v1/shapes").param("type", "SQUARE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Set<Shape> result = objectMapper.readValue(response, new TypeReference<Set<Shape>>() {
        });

        List<Shape> shapes = new ArrayList<>(result);

        assertEquals("SQUARE", shapes.get(0).getType());
        assertEquals(LocalDateTime.of(2023, 1, 1, 1, 1), shapes.get(0).getCreatedAt());
        assertEquals("A", shapes.get(0).getCreatedBy());
        assertEquals(LocalDateTime.of(2023, 1, 1, 1, 1), shapes.get(0).getLastModifiedAt());
        assertEquals(1, shapes.get(0).getVersion());
        assertEquals("A", shapes.get(0).getLastModifiedBy());
        assertEquals(100, shapes.get(0).getArea());
        assertEquals(40, shapes.get(0).getPerimeter());


    }


}