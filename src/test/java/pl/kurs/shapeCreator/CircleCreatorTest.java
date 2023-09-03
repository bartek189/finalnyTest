package pl.kurs.shapeCreator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.kurs.entity.model.Circle;
import pl.kurs.entity.model.User;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.repository.ShapeRepository;
import pl.kurs.util.SecurityUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CircleCreatorTest {
    private CircleCreator circleCreator;
    private ShapeRepository repository;
    private SecurityUtil securityUtil;

    @BeforeEach
    public void init() {
        repository = Mockito.mock(ShapeRepository.class);
        securityUtil = Mockito.mock(SecurityUtil.class);
        circleCreator = new CircleCreator(repository, securityUtil);
    }

    @Test
    void shouldCreateCircle() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        User user = new User("A", "B");

        Mockito.when(securityUtil.getUser()).thenReturn(user);

        ShapeRequest shapeRequest = new ShapeRequest("CIRCLE", List.of(10.));

        circleCreator.createShape(shapeRequest);

        ArgumentCaptor<Circle> argumentCaptor = ArgumentCaptor.forClass(Circle.class);
        Mockito.verify(repository).save(argumentCaptor.capture());
        Circle result = argumentCaptor.getValue();

        assertEquals("CIRCLE", result.getType());
        assertEquals("A", result.getCreatedBy());
        assertEquals("A", result.getLastModifiedBy());
        assertEquals(10., result.getRadius());

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

            ShapeRequest shapeRequest = new ShapeRequest("CIRCLE", List.of(10., 1.));

            circleCreator.createShape(shapeRequest);

        });
        assertEquals("Invalid number of parameters for circle", thrown.getMessage());
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

            ShapeRequest shapeRequest = new ShapeRequest("CIRCLE", List.of(0.));
            circleCreator.createShape(shapeRequest);

        });
        assertEquals("Radius must be greater than 0", thrown.getMessage());
    }
}