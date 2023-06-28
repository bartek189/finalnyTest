package pl.kurs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.kurs.entity.model.Rectangle;
import pl.kurs.entity.model.User;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.repository.ShapeRepository;
import pl.kurs.util.SecurityUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RectangleServiceTest {

    private RectangleService service;
    private ShapeRepository repository;
    private SecurityUtil securityUtil;

    @BeforeEach
    public void init() {
        repository = Mockito.mock(ShapeRepository.class);
        securityUtil = Mockito.mock(SecurityUtil.class);
        service = new RectangleService(repository, securityUtil);
    }

    @Test
    void shouldCreateRectangle() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);


        User user = new User("A", "B");

        Mockito.when(securityUtil.getUser()).thenReturn(user);

        ShapeRequest shapeRequest = new ShapeRequest("RECTANGLE", List.of(3.,2.));

        service.createRectangle(shapeRequest);

        ArgumentCaptor<Rectangle> argumentCaptor = ArgumentCaptor.forClass(Rectangle.class);
        Mockito.verify(repository).save(argumentCaptor.capture());
        Rectangle result = argumentCaptor.getValue();

        assertEquals("RECTANGLE", result.getType());
        assertEquals(6, result.getArea());
        assertEquals(10, result.getPerimeter());
        assertEquals("A", result.getCreatedBy());
        assertEquals("A", result.getLastModifiedBy());
        assertEquals(3.,result.getWidth());
        assertEquals(2.,result.getHeight());
    }

    @Test
    public void shouldThrownInvalidNumberOfParameters_WhenSideIsSmallerThan0() {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            Authentication authentication = Mockito.mock(Authentication.class);
            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);

            User user = new User("A", "B");
            Mockito.when(securityUtil.getUser()).thenReturn(user);

            ShapeRequest shapeRequest = new ShapeRequest("RECTANGLE", List.of(3.));

            service.createRectangle(shapeRequest);

        });
        assertEquals("Invalid number of parameters for rectangle", thrown.getMessage());
    }

    @Test
    public void shouldThrownInvalidNumberOfParameters() {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            Authentication authentication = Mockito.mock(Authentication.class);
            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);

            User user = new User("A", "B");
            Mockito.when(securityUtil.getUser()).thenReturn(user);

            ShapeRequest shapeRequest = new ShapeRequest("RECTANGLE", List.of(0.,0.));
            service.createRectangle(shapeRequest);

        });
        assertEquals("Width or height must be greater than 0", thrown.getMessage());
    }

}