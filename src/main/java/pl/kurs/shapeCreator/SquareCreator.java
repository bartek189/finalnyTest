package pl.kurs.shapeCreator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.service.SquareService;
import pl.kurs.shapeFactory.ShapeService;


@Component
@RequiredArgsConstructor
public class SquareCreator implements ShapeService {

    private final SquareService service;

    @Override
    public String getSupportedType() {
        return "SQUARE";
    }

    @Override
    public ShapeResponse createShape(ShapeRequest shapeRequest) {
        return service.createSquare(shapeRequest);
    }
}
