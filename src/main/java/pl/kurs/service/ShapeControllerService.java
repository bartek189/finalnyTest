package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.kurs.entity.model.FindShapeQuery;
import pl.kurs.entity.model.Shape;
import pl.kurs.repository.ShapeCriteriaRepository;


@Service
@RequiredArgsConstructor
public class ShapeControllerService {
    private final ShapeCriteriaRepository shapeCriteriaRepository;

    public Page<Shape> getShape(FindShapeQuery findShapeQuery) {
        return shapeCriteriaRepository.findAllWithFilters(findShapeQuery);
    }

}
