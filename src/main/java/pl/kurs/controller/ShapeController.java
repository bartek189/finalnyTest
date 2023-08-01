package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shapes")
public class ShapeController {

    private final ShapeFactory shapeFactory;
    private final ShapeControllerService service;

    @PostMapping("/create")
    public ResponseEntity<ShapeResponse> addShape(@RequestBody @Valid ShapeRequest shapeRequest) {
        ShapeResponse shapeResponse = shapeFactory.createShape(shapeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(shapeResponse);
    }

    @GetMapping
    public ResponseEntity<Page<Shape>> getEmployees(@ModelAttribute("findShapeQuery") FindShapeQuery findShapeQuery) {
        return new ResponseEntity<>(service.getShape(findShapeQuery),
                HttpStatus.OK);
    }
}




