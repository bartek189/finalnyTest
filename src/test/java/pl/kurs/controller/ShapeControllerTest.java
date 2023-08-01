package pl.kurs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kurs.entity.model.*;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.repository.RoleRepository;
import pl.kurs.repository.ShapeRepository;
import pl.kurs.repository.UserRepository;
import pl.kurs.service.ShapeControllerService;
import pl.kurs.service.UserPermissionService;
import pl.kurs.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @MockBean
    private ShapeControllerService shapeService;

    @BeforeEach
    public void init() {
        shapeRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void shouldSaveShape() {
        Role role = new Role(ERole.ROLE_CREATOR);
        roleRepository.save(role);
        User user = new User("B", "BB", Set.of(role));
        when(securityUtil.getUser()).thenReturn(user);

        userRepository.save(user);

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
        assertEquals("B", result.getCreatedBy());
        assertEquals("B", result.getLastModifiedBy());
    }

    @Test
    void shouldGetShapeByParameters() throws Exception {
        Role role = new Role(ERole.ROLE_CREATOR);
        roleRepository.save(role);
        User user = new User("A", "B", Collections.singleton(role));
        userRepository.save(user);

        when(securityUtil.getUser()).thenReturn(user);

        Shape shape = new Square("SQUARE", user.getUserName(), LocalDateTime.now(), 1, LocalDateTime.now(), user.getUserName(), user, 10);
        shapeRepository.save(shape);

        FindShapeQuery findShapeQuery = new FindShapeQuery();
        findShapeQuery.setCreatedBy("A");
        findShapeQuery.setType("SQUARE");
        findShapeQuery.setAreaFrom(0.);
        findShapeQuery.setAreaTo(1000.);
        findShapeQuery.setPerimeterFrom(0.);
        findShapeQuery.setPerimeterTo(100.);
        findShapeQuery.setParameterFrom(0.);
        findShapeQuery.setParameterTo(0.);
        findShapeQuery.setParameterFrom2(0.);
        findShapeQuery.setParameterTo2(0.);

        when(shapeService.getShape(any(FindShapeQuery.class))).thenReturn(new PageImpl<>(Collections.singletonList(shape)));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/shapes")
                        .flashAttr("findShapeQuery", findShapeQuery)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].type").value("SQUARE"));

    }
}

