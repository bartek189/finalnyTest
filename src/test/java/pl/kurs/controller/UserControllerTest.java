package pl.kurs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kurs.entity.command.CreateUserCommand;
import pl.kurs.entity.dto.UserCreatedShapesDto;
import pl.kurs.entity.model.User;
import pl.kurs.repository.RoleRepository;
import pl.kurs.repository.UserRepository;
import pl.kurs.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.kurs.entity.model.ERole.ROLE_CREATOR;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository repository;
    @MockBean
    private UserService userService;

    @BeforeEach
    public void init() {
        userRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void shouldSaveUser() {
        CreateUserCommand userCommand = new CreateUserCommand();
        userCommand.setUserName("testUser");
        userCommand.setPassword("testPassword");
        userCommand.setRoleName(ROLE_CREATOR);

        // Przykładowy użytkownik do zwrócenia przez serwis
        User user = new User();
        user.setId(1L);
        user.setUserName("testUser");
        user.setPassword("testPassword");


        Mockito.when(userService.saveNewCreator(Mockito.any(CreateUserCommand.class))).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.post("/register/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCommand)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userName").value("testUser"))
                .andExpect(jsonPath("$.password").value("testPassword"));

    }

    @Test
    public void testGetShapes() throws Exception {
        User user = new User();
        user.setUserName("testUser");

        Mockito.when(userService.shapes("testUser")).thenReturn(new UserCreatedShapesDto(user));

        mvc.perform(MockMvcRequestBuilders.get("/shapes")
                        .param("name", "testUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.userName").value("testUser"))
                .andExpect(jsonPath("$.shapesSize").value(0));
    }
}