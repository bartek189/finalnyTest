package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.entity.model.Circle;
import pl.kurs.entity.model.Shape;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.CircleResponse;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.repository.ShapeRepository;
import pl.kurs.util.SecurityUtil;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CircleService {
    private final ShapeRepository shapeRepository;
    private final SecurityUtil securityUtil;

    public ShapeResponse createCircle(ShapeRequest shapeRequest) {
        String user = securityUtil.getUser().getUserName();
        List<Double> parameters = shapeRequest.getParameters();
        double r = parameters.get(0);
        validateCircleParameter(parameters);

        Shape shape = new Circle("CIRCLE", LocalDateTime.now(), 1, user, LocalDateTime.now(), user, Math.PI * r * r, 2 * Math.PI * r, r);

        shapeRepository.save(shape);

        return createCircleResponse(r, shape);
    }

    private static ShapeResponse createCircleResponse(double r, Shape shape) {
        ShapeResponse shapeResponse = new CircleResponse(r);
        shapeResponse.setId(shape.getId());
        shapeResponse.setType("CIRCLE");
        shapeResponse.setCreatedAt(shape.getCreatedAt());
        shapeResponse.setVersion(shape.getVersion());
        shapeResponse.setCreatedBy(shape.getCreatedBy());
        shapeResponse.setLastModifiedAt(shape.getLastModifiedAt());
        shapeResponse.setLastModifiedBy(shape.getLastModifiedBy());
        shapeResponse.setArea(shape.getArea());
        shapeResponse.setPerimeter(shape.getPerimeter());
        return shapeResponse;
    }

    private void validateCircleParameter(List<Double> parameters) {
        if (parameters.size() != 1) {
            throw new IllegalArgumentException("Invalid number of parameters for circle");
        }
        if (parameters.get(0) <= 0) {
            throw new IllegalArgumentException("Radius must be greater than 0");
        }
    }
}
