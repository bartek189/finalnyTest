package pl.kurs.shapeCreator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.service.CircleService;
import pl.kurs.shapeFactory.ShapeService;

@Component
@RequiredArgsConstructor
public class CircleCreator implements ShapeService {

    private final CircleService service;

    @Override
    public String getSupportedType() {
        return "CIRCLE";
    }

    @Override
    public ShapeResponse createShape(ShapeRequest shapeRequest) {

        return service.createCircle(shapeRequest);
    }
}
