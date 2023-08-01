package pl.kurs.shapeCreator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kurs.entity.model.Rectangle;
import pl.kurs.entity.model.Shape;
import pl.kurs.entity.model.User;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.RectangleResponse;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.repository.ShapeRepository;
import pl.kurs.shapeFactory.ShapeService;
import pl.kurs.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RectangleCreator implements ShapeService {
    private final ShapeRepository shapeRepository;
    private final SecurityUtil securityUtil;

    @Override
    public String getSupportedType() {
        return "RECTANGLE";
    }

    @Override
    public ShapeResponse createShape(ShapeRequest shapeRequest) {


        User user = securityUtil.getUser();
        List<Double> parameters = shapeRequest.getParameters();
        validateRectangleParameters(parameters);
        double w = parameters.get(0);
        double h = parameters.get(1);

        Shape shape = new Rectangle("RECTANGLE", user.getUserName(), LocalDateTime.now(), 1, LocalDateTime.now(), user.getUserName(), user, w, h);
        Map<String, Double> map = new HashMap<>();
        map.put("width", w);
        map.put("height", h);

        ((Rectangle) shape).setDimensions(w, h);
        shapeRepository.save(shape);
        return createRectangleResponse(w, h, shape);
    }


    private void validateRectangleParameters(List<Double> parameters) {
        if (parameters.size() != 2) {
            throw new IllegalArgumentException("Invalid number of parameters for rectangle");
        }
        if (parameters.get(0) <= 0 || parameters.get(1) <= 0) {
            throw new IllegalArgumentException("Width or height must be greater than 0");
        }

    }

    private ShapeResponse createRectangleResponse(double w, double h, Shape shape) {
        ShapeResponse shapeResponse = new RectangleResponse(w, h);
        shapeResponse.setId(shape.getId());
        shapeResponse.setType(shape.getType());
        shapeResponse.setCreatedAt(shape.getCreatedAt());
        shapeResponse.setVersion(shape.getVersion());
        shapeResponse.setLastModifiedAt(shape.getLastModifiedAt());
        shapeResponse.setLastModifiedBy(shape.getLastModifiedBy());
        shapeResponse.setArea(shape.area());
        shapeResponse.setPerimeter(shape.perimeter());
        return shapeResponse;
    }
}

