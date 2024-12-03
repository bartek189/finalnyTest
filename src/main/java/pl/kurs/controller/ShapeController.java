package pl.kurs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.entity.model.FindShapeQuery;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.repository.ShapeQueryRepository;
import pl.kurs.shapeFactory.ShapeFactory;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shapes")
public class ShapeController {

    private final ShapeFactory shapeFactory;
    private final ShapeQueryRepository shapeQueryRepository;

    @PostMapping("/create")
    public ResponseEntity<ShapeResponse> addShape(@RequestBody @Valid ShapeRequest shapeRequest) {
        ShapeResponse shapeResponse = shapeFactory.createShape(shapeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(shapeResponse);
    }

    @GetMapping
    public ResponseEntity<List<ShapeResponse>> getShape(@ModelAttribute("findShapeQuery") FindShapeQuery query) throws SQLException {
        List<ShapeResponse> shapeSet = shapeQueryRepository.findAllShape(query);
        return ResponseEntity.status(HttpStatus.OK).body(shapeSet);
    }
}





