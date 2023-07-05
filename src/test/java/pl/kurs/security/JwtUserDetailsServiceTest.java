package pl.kurs.security;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pl.kurs.entity.model.ERole;
import pl.kurs.entity.model.Role;
import pl.kurs.entity.model.User;
import pl.kurs.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class JwtUserDetailsServiceTest {

    private JwtUserDetailsService jwtUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);

        jwtUserDetailsService = new JwtUserDetailsService(userRepository);
    }

    @Test
    public void testUserDetails() {
        User user = new User("A", "B");
        user.setRoles(Collections.singleton(new Role(ERole.ROLE_CREATOR)));
        Mockito.when(userRepository.findByUserName(user.getUserName())).thenReturn(Optional.of(user));

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.getUserName());

        assertNotNull(userDetails);
        assertEquals(user.getUserName(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CREATOR")));


    }

    @Test
    public void loadUserByUsername_NonExistingUsername_ThrowsUsernameNotFoundException() {
        String username = "q";

        Mockito.when(userRepository.findByUserName(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            jwtUserDetailsService.loadUserByUsername(username);
        });
    }
}