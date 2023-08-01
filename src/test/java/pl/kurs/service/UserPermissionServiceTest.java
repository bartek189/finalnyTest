package pl.kurs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.kurs.entity.model.ERole;
import pl.kurs.entity.model.Role;
import pl.kurs.entity.model.User;
import pl.kurs.service.UserPermissionService;
import pl.kurs.util.SecurityUtil;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserPermissionServiceTest {


    private UserPermissionService userPermissionService;

    private SecurityUtil securityUtil;

    @BeforeEach
    public void init() {
        securityUtil = Mockito.mock(SecurityUtil.class);
        userPermissionService = new UserPermissionService(securityUtil);
    }

    @Test
    public void shouldPassCreatorRole() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);


        User user = new User("A", "B");
        Role role = new Role(ERole.ROLE_CREATOR);
        user.setRoles(Set.of(role));
        Mockito.when(securityUtil.getUser()).thenReturn(user);
        userPermissionService.validateRequest();

    }

    @Test
    public void shouldThrowUnauthorizedException() {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {

            Authentication authentication = Mockito.mock(Authentication.class);
            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);

            User user = new User("A", "B");
            Role role = new Role(ERole.ROLE_USER);
            user.setRoles(Set.of(role));
            Mockito.when(securityUtil.getUser()).thenReturn(user);

            userPermissionService.validateRequest();

        });
        assertEquals("UNAUTHORIZED", thrown.getMessage());


    }

}