package pl.kurs.shapeFactory;

import pl.kurs.entity.response.ShapeResponse;

import java.sql.ResultSet;

public interface ShapeDtoService {
    String getSupportedType();

    ShapeResponse createShapeFromResultSet(ResultSet resultSet);
}
