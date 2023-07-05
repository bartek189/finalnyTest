package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.entity.model.Shape;
import pl.kurs.entity.model.Square;
import pl.kurs.entity.request.ShapeRequest;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.entity.response.SquareResponse;
import pl.kurs.repository.ShapeRepository;
import pl.kurs.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SquareService {
    private final ShapeRepository shapeRepository;
    private final SecurityUtil securityUtil;

    public ShapeResponse createSquare(ShapeRequest shapeRequest) {
        String user = securityUtil.getUser().getUserName();
        List<Double> parameters = shapeRequest.getParameters();
        validateSquareParameter(parameters);
        double side = parameters.get(0);

        Square square = new Square("SQUARE", LocalDateTime.now(), 1, user, LocalDateTime.now(), user, side * side, side * 4, side);
        shapeRepository.save(square);
        return createSquareResponse(side, square);
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
        shapeResponse.setCreatedBy(shape.getCreatedBy());
        shapeResponse.setLastModifiedAt(shape.getLastModifiedAt());
        shapeResponse.setLastModifiedBy(shape.getLastModifiedBy());
        shapeResponse.setArea(shape.getArea());
        shapeResponse.setPerimeter(shape.getPerimeter());
        return shapeResponse;
    }
}
