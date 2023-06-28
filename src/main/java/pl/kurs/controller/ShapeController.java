package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.shapeFactory.ShapeFactory;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ShapeController {

    private final ShapeFactory shapeFactory;


    @PostMapping("/shapes")
    public ResponseEntity<ShapeResponse> addShape(@RequestBody ShapeRequest shapeRequest) {
        ShapeResponse shapeResponse = shapeFactory.createShape(shapeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(shapeResponse);
    }


}
