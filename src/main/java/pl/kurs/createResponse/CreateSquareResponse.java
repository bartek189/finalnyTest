package pl.kurs.createResponse;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.entity.response.SquareResponse;
import pl.kurs.shapeFactory.ShapeDtoService;

import java.sql.ResultSet;
import java.time.LocalDateTime;

@Component
public class CreateSquareResponse implements ShapeDtoService {
    @Override
    public String getSupportedType() {
        return "SQUARE";
    }


    @Override
    @SneakyThrows
    public ShapeResponse createShapeFromResultSet(ResultSet resultSet) {
        Long id = resultSet.getLong("ID");
        String type = resultSet.getString("TYPE");
        int version = resultSet.getInt("VERSION");
        LocalDateTime createdAt = resultSet.getObject("CREATED_AT", LocalDateTime.class);
        String createdBy = resultSet.getString("CREATED_BY");
        LocalDateTime lastModifiedAt = resultSet.getObject("LAST_MODIFIED_AT", LocalDateTime.class);
        String lastModifiedBy = resultSet.getString("LAST_MODIFIED_BY");
        double side = resultSet.getDouble("SIDE");
        double area = resultSet.getDouble("AREA");
        double perimeter = resultSet.getDouble("PERIMETER");


        SquareResponse response = new SquareResponse(side);
        response.setId(id);
        response.setType(type);
        response.setVersion(version);
        response.setCreatedAt(createdAt);
        response.setCreatedBy(createdBy);
        response.setLastModifiedAt(lastModifiedAt);
        response.setLastModifiedBy(lastModifiedBy);
        response.setArea(area);
        response.setPerimeter(perimeter);


        return response;
    }
}
