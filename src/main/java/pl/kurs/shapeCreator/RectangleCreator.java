package pl.kurs.shapeCreator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.service.RectangleService;
import pl.kurs.shapeFactory.ShapeService;

@Component
@RequiredArgsConstructor
public class RectangleCreator implements ShapeService {
    private final RectangleService service;

    @Override
    public String getSupportedType() {
        return "RECTANGLE";
    }

    @Override
    public ShapeResponse createShape(ShapeRequest shapeRequest) {


        return service.createRectangle(shapeRequest);
    }
}

