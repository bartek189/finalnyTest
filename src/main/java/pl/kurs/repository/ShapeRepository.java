package pl.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.entity.model.Shape;

public interface ShapeRepository extends JpaRepository<Shape, Long> {

}
