//package pl.kurs.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import pl.kurs.entity.model.Circle;
//import pl.kurs.entity.model.Shape;
//import pl.kurs.repository.ShapeRepository;
//import java.time.LocalDateTime;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//
//
//class ShapeControllerServiceTest {
//    @Mock
//    private ShapeRepository shapeRepository;
//
//    private ShapeControllerService shapeControllerService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        shapeControllerService = new ShapeControllerService(shapeRepository);
//    }
//
//    @Test
//    public void testGetShapesByParameters() {
//        String createdBy = "John";
//        String type = "CIRCLE";
//        Double areaFrom = 10.0;
//        Double areaTo = 100.0;
//        Double perimeterFrom = 10.0;
//        Double perimeterTo = 200.0;
//        Double sideFrom = 0.0;
//        Double sideTo = 0.0;
//        Double radiusFrom = 0.0;
//        Double radiusTo = 50.0;
//        Double widthFrom = 0.0;
//        Double widthTo = 0.0;
//        Double heightFrom = 0.0;
//        Double heightTo = 0.0;
//
//
//        Shape s = new Circle(type, LocalDateTime.now(), 1, createdBy, LocalDateTime.now(), "John", 100, 11, 2);
//        Set<Shape> expectedShapes = Set.of(s);
//
//        when(shapeRepository.searchShapes(eq(createdBy), eq(type), eq(areaFrom), eq(areaTo), eq(perimeterFrom),
//                eq(perimeterTo), eq(sideFrom), eq(sideTo), eq(radiusFrom), eq(radiusTo), eq(widthFrom), eq(widthTo),
//                eq(heightFrom), eq(heightTo)))
//                .thenReturn(expectedShapes);
//
//
//        Set<Shape> response = shapeControllerService.getShapesByParameters(createdBy, type, areaFrom, areaTo,
//                perimeterFrom, perimeterTo, sideFrom, sideTo, radiusFrom, radiusTo, widthFrom, widthTo, heightFrom,
//                heightTo);
//
//        assertEquals(expectedShapes, response);
//    }
//}