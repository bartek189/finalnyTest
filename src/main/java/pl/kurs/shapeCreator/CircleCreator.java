package pl.kurs.shapeCreator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kurs.entity.model.Circle;
import pl.kurs.entity.model.Shape;
import pl.kurs.entity.model.User;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.CircleResponse;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.repository.ShapeRepository;
import pl.kurs.shapeFactory.ShapeService;
import pl.kurs.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CircleCreator implements ShapeService {

    private final ShapeRepository shapeRepository;

    private final SecurityUtil securityUtil;

    @Override
    public String getSupportedType() {
        return "CIRCLE";
    }

    @Override
    public ShapeResponse createShape(ShapeRequest shapeRequest) {

        User user = securityUtil.getUser();
        List<Double> parameters = shapeRequest.getParameters();
        double r = parameters.get(0);
        validateCircleParameter(parameters);


        Circle shape = new Circle("CIRCLE", user.getUserName(), LocalDateTime.now(), 1, LocalDateTime.now(), user.getUserName(), user, r);

        shapeRepository.save(shape);
        return createCircleResponse(r, shape);
    }

    private ShapeResponse createCircleResponse(double r, Shape shape) {
        User user = securityUtil.getUser();

        ShapeResponse shapeResponse = new CircleResponse(r);
        shapeResponse.setId(shape.getId());
        shapeResponse.setType("CIRCLE");
        shapeResponse.setCreatedAt(shape.getCreatedAt());
        shapeResponse.setVersion(shape.getVersion());
        shapeResponse.setCreatedBy(user.getUserName());
        shapeResponse.setLastModifiedAt(shape.getLastModifiedAt());
        shapeResponse.setLastModifiedBy(shape.getLastModifiedBy());
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
