package pl.kurs.shapeCreator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kurs.entity.model.Shape;
import pl.kurs.entity.model.Square;
import pl.kurs.entity.model.User;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.entity.response.SquareResponse;
import pl.kurs.repository.ShapeRepository;
import pl.kurs.shapeFactory.ShapeService;
import pl.kurs.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class SquareCreator implements ShapeService {

    private final ShapeRepository shapeRepository;
    private final SecurityUtil securityUtil;

    @Override
    public String getSupportedType() {
        return "SQUARE";
    }

    @Override
    public ShapeResponse createShape(ShapeRequest shapeRequest) {


        User user = securityUtil.getUser();
        List<Double> parameters = shapeRequest.getParameters();
        validateSquareParameter(parameters);
        double side = parameters.get(0);

        Shape shape = new Square("SQUARE", user.getUserName(), LocalDateTime.now(), 1, LocalDateTime.now(), user.getUserName(), user, side);
        Map<String, Double> map = new HashMap<>();
        map.put("side", side);
        ((Square) shape).setSide(side);
        shapeRepository.save(shape);
        return createSquareResponse(side, shape);
    }

    private void validateSquareParameter(List<Double> parameters) {
        if (parameters.size() != 1) {
            throw new IllegalArgumentException("Invalid number of parameters for square");
        }
        if (parameters.get(0) <= 0) {
            throw new IllegalArgumentException("Side must be greater than 0");
        }
    }

    private ShapeResponse createSquareResponse(double side, Shape shape) {
        ShapeResponse shapeResponse = new SquareResponse(side);
        shapeResponse.setId(shape.getId());
        shapeResponse.setType("SQUARE");
        shapeResponse.setCreatedAt(shape.getCreatedAt());
        shapeResponse.setVersion(shape.getVersion());
        shapeResponse.setCreatedBy(shape.getUser().getUserName());
        shapeResponse.setLastModifiedAt(shape.getLastModifiedAt());
        shapeResponse.setLastModifiedBy(shape.getLastModifiedBy());
        shapeResponse.setArea(shape.area());
        shapeResponse.setPerimeter(shape.perimeter());
        return shapeResponse;
    }
}

