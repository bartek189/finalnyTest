package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.entity.model.Rectangle;
import pl.kurs.entity.model.Shape;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.RectangleResponse;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.repository.ShapeRepository;
import pl.kurs.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RectangleService {

    private final ShapeRepository shapeRepository;
    private final SecurityUtil securityUtil;

    public ShapeResponse createRectangle(ShapeRequest shapeRequest) {
        String user = securityUtil.getUser().getUserName();
        List<Double> parameters = shapeRequest.getParameters();
        validateRectangleParameters(parameters);
        double w = parameters.get(0);
        double h = parameters.get(1);

        Shape shape = new Rectangle("RECTANGLE", LocalDateTime.now(), 1, user, LocalDateTime.now(), user, w * h, 2 * w + 2 * h, w, h);
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
        shapeResponse.setCreatedBy(shape.getCreatedBy());
        shapeResponse.setLastModifiedAt(shape.getLastModifiedAt());
        shapeResponse.setLastModifiedBy(shape.getLastModifiedBy());
        shapeResponse.setArea(shape.getArea());
        shapeResponse.setPerimeter(shape.getPerimeter());
        return shapeResponse;
    }
}
