package pl.kurs.shapeCreator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.kurs.entity.model.Square;
import pl.kurs.entity.model.User;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.repository.ShapeRepository;
import pl.kurs.util.SecurityUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SquareServiceTest {

    private SquareCreator createSquare;
    private ShapeRepository repository;
    private SecurityUtil securityUtil;

    @BeforeEach
    public void init() {
        repository = Mockito.mock(ShapeRepository.class);
        securityUtil = Mockito.mock(SecurityUtil.class);
        createSquare = new SquareCreator(repository, securityUtil);
    }

    @Test
    void shouldCreateSquare() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);


        User user = new User("A", "B");

        Mockito.when(securityUtil.getUser()).thenReturn(user);

        ShapeRequest shapeRequest = new ShapeRequest("SQUARE", List.of(10.));

        createSquare.createShape(shapeRequest);

        ArgumentCaptor<Square> argumentCaptor = ArgumentCaptor.forClass(Square.class);
        Mockito.verify(repository).save(argumentCaptor.capture());
        Square result = argumentCaptor.getValue();

        assertEquals("SQUARE", result.getType());
        assertEquals("A", result.getCreatedBy());
        assertEquals("A", result.getLastModifiedBy());
        assertEquals(10, result.getSide());
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

            ShapeRequest shapeRequest = new ShapeRequest("SQUARE", List.of(10., 1.));

            createSquare.createShape(shapeRequest);

        });
        assertEquals("Invalid number of parameters for square", thrown.getMessage());
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

            ShapeRequest shapeRequest = new ShapeRequest("SQUARE", List.of(0.));
            createSquare.createShape(shapeRequest);

        });
        assertEquals("Side must be greater than 0", thrown.getMessage());
    }
}
