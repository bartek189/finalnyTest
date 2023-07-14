package pl.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import pl.kurs.entity.model.Shape;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Set;

public interface ShapeRepository extends JpaRepository<Shape, Long> {
    @Query("SELECT s FROM Shape s JOIN s.parameters p WHERE (KEY(p) = :paramKey AND p >= :paramFrom AND p <= :paramTo) " +
            "AND :createdBy IS NULL OR s.createdBy = :createdBy " +
            "AND :type IS NULL OR s.type = :type " +
            "AND :areaFrom IS NULL OR s.area >= :areaFrom " +
            "AND :areaTo IS NULL OR s.area <= :areaTo " +
            "AND :perimeterFrom IS NULL OR s.perimeter >= :perimeterFrom " +
            "AND :perimeterTo IS NULL OR s.perimeter <= :perimeterTo")
    Set<Shape> searchShapes(@Param("paramKey") String paramKey,
                            @Param("paramFrom") Double paramFrom,
                            @Param("paramTo") Double paramTo,
                            @Param("createdBy") String createdBy,
                            @Param("type") String type,
                            @Param("areaFrom") Double areaFrom,
                            @Param("areaTo") Double areaTo,
                            @Param("perimeterFrom") Double perimeterFrom,
                            @Param("perimeterTo") Double perimeterTo);
}