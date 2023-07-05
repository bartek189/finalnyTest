package pl.kurs.shapeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kurs.entity.model.Shape;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.service.UserPermissionService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ShapeFactory {
    private final UserPermissionService userPermissionService;
    private final Map<String, ShapeService> shapeMap;


    @Autowired
    public ShapeFactory(UserPermissionService userPermissionService, List<ShapeService> shapeList) {
        this.userPermissionService = userPermissionService;
        this.shapeMap = shapeList.stream()
                .collect(Collectors.toMap(ShapeService::getSupportedType, Function.identity()));
    }

    public ShapeResponse createShape(ShapeRequest shapeRequestDto) {
        userPermissionService.validateRequest();
        String shapeType = shapeRequestDto.getType();
        ShapeService shape = shapeMap.get(shapeType);
        if (shape == null) {
            throw new IllegalArgumentException("Invalid shape type");
        }
        return shape.createShape(shapeRequestDto);
    }
}
