package pl.kurs.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kurs.entity.model.*;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.repository.RoleRepository;
import pl.kurs.repository.ShapeQueryRepository;
import pl.kurs.repository.ShapeRepository;
import pl.kurs.repository.UserRepository;
import pl.kurs.service.UserPermissionService;
import pl.kurs.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
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

    @Autowired
    private ShapeQueryRepository shapeQueryRepository;


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

        Square shape = new Square("SQUARE", user.getUserName(), LocalDateTime.now(), 1, LocalDateTime.now(), user.getUserName(), user, 10);
        shapeRepository.save(shape);

        FindShapeQuery findShapeQuery = new FindShapeQuery();
        findShapeQuery.setCreated_By("C");
        findShapeQuery.setType("SQUARE");


        String s = mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/shapes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("Type", findShapeQuery.getType()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<ShapeResponse> responses = objectMapper.readValue(s, new TypeReference<List<ShapeResponse>>() {
        });

        assertEquals("SQUARE", responses.get(0).getType());

    }
}