package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.entity.model.Shape;
import pl.kurs.repository.ShapeRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShapeControllerService {
    private final ShapeRepository shapeRepository;


    public Set<Shape> getShapesByParameters(String createdBy, String type, Double areaFrom, Double areaTo, Double perimeterFrom,
                                            Double perimeterTo, Double sideFrom, Double sideTo, Double radiusFrom, Double radiusTo,
                                            Double widthFrom, Double widthTo, Double heightFrom, Double heightTo) {
        return shapeRepository.searchShapes(createdBy, type, areaFrom, areaTo, perimeterFrom, perimeterTo, sideFrom, sideTo, radiusFrom, radiusTo, widthFrom, widthTo, heightFrom, heightTo);
    }

}
