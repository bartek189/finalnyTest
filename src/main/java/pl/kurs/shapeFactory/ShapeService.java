package pl.kurs.shapeFactory;

import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.ShapeResponse;


public interface ShapeService {
    String getSupportedType();

    ShapeResponse createShape(ShapeRequest shapeRequest);
}
