package pl.kurs.shapeFactory;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kurs.entity.response.ShapeResponse;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ShapeDtoFactory {
    private final Map<String, ShapeDtoService> shapeMap;

    @Autowired
    public ShapeDtoFactory(List<ShapeDtoService> shapeList) {
        this.shapeMap = shapeList.stream()
                .collect(Collectors.toMap(ShapeDtoService::getSupportedType, Function.identity()));
    }

    @SneakyThrows
    public ShapeResponse getShapeDtoService(ResultSet resultSet) {
        String shapeType = resultSet.getString("type");
        ShapeDtoService service = getService(shapeType);

        return service.createShapeFromResultSet(resultSet);
    }

    private ShapeDtoService getService(String shapeType) {
        return Optional.ofNullable(shapeMap.get(shapeType))
                .orElseThrow(() -> new IllegalArgumentException("Not implemented for shape type: " + shapeType));
    }


}
