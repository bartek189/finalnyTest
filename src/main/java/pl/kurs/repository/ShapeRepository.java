package pl.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kurs.entity.model.Shape;

import java.util.Set;

public interface ShapeRepository extends JpaRepository<Shape, Long> {


    @Query("SELECT s FROM Shape s " +
            "WHERE (:createdBy IS NULL OR s.createdBy = :createdBy) " +
            "AND (:type IS NULL OR s.type = :type) " +
            "AND (:areaFrom IS NULL OR s.area >= :areaFrom)" +
            "AND (:areaTo IS NULL OR s.area <= :areaTo) " +
            "AND (:perimeterFrom IS NULL OR s.perimeter >= :perimeterFrom) " +
            "AND (:perimeterTo IS NULL OR s.perimeter <= :perimeterTo) " +
            "AND (:sideFrom Is NULL OR s.side >= :sideFrom)" +
            "AND (:sideTo IS NULL OR s.side >= :sideTo)" +
            "AND (:radiusFrom IS NULL OR s.radius >= :radiusFrom)" +
            "AND (:radiusTo IS NULL OR s.radius <= :radiusTo)" +
            "AND (:widthFrom IS NULL OR s.width >= :widthFrom)" +
            "AND (:widthTo IS NULL OR s.width <= :widthTo)" +
            "AND (:heightFrom IS NULL OR s.height <= :heightFrom)" +
            "AND (:heightTo IS NULL OR s.height >= :heightTo)"

    )
    Set<Shape> searchShapes(@Param("createdBy") String createdBy,
                            @Param("type") String type,
                            @Param("areaFrom") Double areaFrom,
                            @Param("areaTo") Double areaTo,
                            @Param("perimeterFrom") Double perimeterFrom,
                            @Param("perimeterTo") Double perimeterTo,
                            @Param("sideFrom") Double sideFrom,
                            @Param("sideTo") Double sideTo,
                            @Param("radiusFrom") Double radiusFrom,
                            @Param("radiusTo") Double radiusTo,
                            @Param("widthFrom") Double widthFrom,
                            @Param("widthTo") Double widthTo,
                            @Param("heightFrom") Double heightFrom,
                            @Param("heightTo") Double heightTo
    );
}

