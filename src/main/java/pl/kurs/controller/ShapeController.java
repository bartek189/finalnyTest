package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.entity.model.FindShapeQuery;
import pl.kurs.entity.model.Shape;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.repository.ShapeRepository;
import pl.kurs.service.ShapeControllerService;
import pl.kurs.shapeFactory.ShapeFactory;

import javax.validation.Valid;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shapes")
public class ShapeController {

    private final ShapeFactory shapeFactory;
    private final ShapeControllerService service;
    private final ShapeRepository shapeRepository;

    @PostMapping("/create")
    public ResponseEntity<ShapeResponse> addShape(@RequestBody @Valid ShapeRequest shapeRequest) {
        ShapeResponse shapeResponse = shapeFactory.createShape(shapeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(shapeResponse);
    }

    @GetMapping()
    public ResponseEntity<Set<Shape>> getShape(@ModelAttribute("findShapeQuery") FindShapeQuery findShapeQuery) {
        Set<Shape> shapes = service.getShapesByParameters(findShapeQuery.getCreatedBy(),
                findShapeQuery.getType(),
                findShapeQuery.getAreaFrom(),
                findShapeQuery.getAreaTo(),
                findShapeQuery.getPerimeterFrom(),
                findShapeQuery.getPerimeterTo(),
                findShapeQuery.getParameterName(),
                findShapeQuery.getValueFrom(),
                findShapeQuery.getValueTo()

        );

        return ResponseEntity.status(HttpStatus.OK).body(shapes);
    }
}




