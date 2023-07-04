package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.entity.model.Shape;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.service.ShapeControllerService;
import pl.kurs.shapeFactory.ShapeFactory;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ShapeController {

    private final ShapeFactory shapeFactory;
    private final ShapeControllerService service;

    @PostMapping("/shapes")
    public ResponseEntity<ShapeResponse> addShape(@RequestBody ShapeRequest shapeRequest) {
        ShapeResponse shapeResponse = shapeFactory.createShape(shapeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(shapeResponse);
    }

    @GetMapping()
    public ResponseEntity<Set<Shape>> getShape(@RequestParam(value = "createdBy", required = false) String createdBy, @RequestParam(value = "type", required = false) String type,
                                               @RequestParam(value = "areaFrom", required = false) Double areaFrom, @RequestParam(value = "areaTo", required = false) Double areaTo,
                                               @RequestParam(value = "perimeterFrom", required = false) Double perimeterFrom, @RequestParam(value = "perimeterTo", required = false) Double perimeterTo,
                                               @RequestParam(value = "sideFrom", required = false) Double sideFrom, @RequestParam(value = "sideTo", required = false) Double sideTo,
                                               @RequestParam(value = "radiusFrom", required = false) Double radiusFrom, @RequestParam(value = "radiusTo", required = false) Double radiusTo,
                                               @RequestParam(value = "widthFrom", required = false) Double widthFrom, @RequestParam(value = "widthTo", required = false) Double widthTo,
                                               @RequestParam(value = "heightFrom", required = false) Double heightFrom, @RequestParam(value = "heightTo", required = false) Double heightTo) {
        Set<Shape> shapes = service.getShapesByParameters(createdBy, type, areaFrom, areaTo, perimeterFrom, perimeterTo, sideFrom, sideTo, radiusFrom, radiusTo, widthFrom, widthTo, heightFrom, heightTo);
        return ResponseEntity.status(HttpStatus.OK).body(shapes);
    }


}
