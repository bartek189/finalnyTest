package pl.kurs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import pl.kurs.entity.model.ERole;
import pl.kurs.entity.model.User;
import pl.kurs.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;


    @BeforeEach
    public void init() {
        userRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void shouldSaveUser() {
        User user = new User("B", "G");

        String json = objectMapper.writeValueAsString(user);

        String response = mvc.perform(post("/register/creator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        User result = objectMapper.readValue(response, User.class);
        boolean a = encoder.matches("G", result.getPassword());


        assertEquals(1, userRepository.findAll().size());
        assertEquals("B", result.getUserName());
        assertTrue(a);
    }


}