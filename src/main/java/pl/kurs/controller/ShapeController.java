package pl.kurs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.repository.ShapeQueryRepository;
import pl.kurs.repository.ShapeRepository;
import pl.kurs.shapeFactory.ShapeFactory;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shapes")
public class ShapeController {

    private final ShapeFactory shapeFactory;
    private final ShapeQueryRepository shapeQueryRepository;

    private final ShapeRepository shapeRepository;
    @PostMapping("/create")
    public ResponseEntity<ShapeResponse> addShape(@RequestBody @Valid ShapeRequest shapeRequest) {
        ShapeResponse shapeResponse = shapeFactory.createShape(shapeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(shapeResponse);
    }

   @GetMapping
   public ResponseEntity<String> getShape() {


       String shapeSet = shapeQueryRepository.findAllShape().toString();
       return ResponseEntity.status(HttpStatus.OK).body(shapeSet);
   }
}





