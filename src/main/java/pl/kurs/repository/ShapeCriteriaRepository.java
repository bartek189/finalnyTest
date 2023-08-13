package pl.kurs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import pl.kurs.entity.model.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class ShapeCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ShapeCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Shape> findAllWithFilters(FindShapeQuery findShapeQuery) {
        CriteriaQuery<Circle> circleQuery = criteriaBuilder.createQuery(Circle.class);
        CriteriaQuery<Square> squareQuery = criteriaBuilder.createQuery(Square.class);
        CriteriaQuery<Rectangle> rectangleQuery = criteriaBuilder.createQuery(Rectangle.class);


        Root<Circle> circleRoot = circleQuery.from(Circle.class);
        Root<Square> squareRoot = squareQuery.from(Square.class);
        Root<Rectangle> rectangleRoot = rectangleQuery.from(Rectangle.class);


        Predicate predicateCircle = getPredicateCircle(findShapeQuery, circleRoot);
        Predicate predicateSquare = getPredicateSquare(findShapeQuery, squareRoot);
        Predicate predicateRectangle = getPredicateRectangle(findShapeQuery, rectangleRoot);

        circleQuery.where(predicateCircle);
        squareQuery.where(predicateSquare);
        rectangleQuery.where(predicateRectangle);

        TypedQuery<Circle> circleTypedQuery = entityManager.createQuery(circleQuery);
        TypedQuery<Square> squareTypedQuery = entityManager.createQuery(squareQuery);
        TypedQuery<Rectangle> rectangleTypedQuery = entityManager.createQuery(rectangleQuery);


        List<Shape> resultList = new ArrayList<>();
        resultList.addAll(circleTypedQuery.getResultList());
        resultList.addAll(squareTypedQuery.getResultList());
        resultList.addAll(rectangleTypedQuery.getResultList());

        return new PageImpl<>(resultList);
    }

    private Predicate getPredicateRectangle(FindShapeQuery findShapeQuery, Root<Rectangle> rectangleRoot) {
        Set<Predicate> predicates = new HashSet<>();

        if (Objects.nonNull(findShapeQuery.getCreatedBy())) {
            predicates.add(criteriaBuilder.equal(rectangleRoot.get("createdBy"), findShapeQuery.getCreatedBy()));
        }
        if (Objects.nonNull(findShapeQuery.getType())) {
            predicates.add(criteriaBuilder.like(rectangleRoot.get("type"), "%" + findShapeQuery.getType() + "%"));
        }
        if (Objects.nonNull(findShapeQuery.getAreaFrom())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(rectangleRoot.get("area"), findShapeQuery.getAreaFrom()));
        }
        if (Objects.nonNull(findShapeQuery.getAreaTo())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(rectangleRoot.get("area"), findShapeQuery.getAreaTo()));
        }
        if (Objects.nonNull(findShapeQuery.getPerimeterFrom())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(rectangleRoot.get("perimeter"), findShapeQuery.getPerimeterFrom()));
        }
        if (Objects.nonNull(findShapeQuery.getPerimeterTo())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(rectangleRoot.get("perimeter"), findShapeQuery.getPerimeterTo()));
        }
        if (Objects.nonNull(findShapeQuery.getParameterFrom())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(rectangleRoot.get("width"), findShapeQuery.getParameterFrom()));
        }
        if (Objects.nonNull(findShapeQuery.getParameterTo())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(rectangleRoot.get("width"), findShapeQuery.getParameterTo()));
        }
        if (Objects.nonNull(findShapeQuery.getParameterFrom2())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(rectangleRoot.get("height"), findShapeQuery.getParameterFrom2()));
        }
        if (Objects.nonNull(findShapeQuery.getParameterTo2())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(rectangleRoot.get("height"), findShapeQuery.getParameterTo2()));
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getPredicateCircle(FindShapeQuery findShapeQuery, Root<Circle> circleRoot) {
        Set<Predicate> predicates = new HashSet<>();
        if (Objects.nonNull(findShapeQuery.getCreatedBy())) {
            predicates.add(criteriaBuilder.equal(circleRoot.get("createdBy"), findShapeQuery.getCreatedBy()));
        }
        if (Objects.nonNull(findShapeQuery.getType())) {
            predicates.add(criteriaBuilder.like(circleRoot.get("type"), "%" + findShapeQuery.getType() + "%"));
        }
        if (Objects.nonNull(findShapeQuery.getParameterFrom())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(circleRoot.get("radius"), findShapeQuery.getParameterFrom()));
        }
        if (Objects.nonNull(findShapeQuery.getParameterTo())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(circleRoot.get("radius"), findShapeQuery.getParameterTo()));
        }
        if (Objects.nonNull(findShapeQuery.getAreaFrom())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(circleRoot.get("area"), findShapeQuery.getAreaFrom()));
        }
        if (Objects.nonNull(findShapeQuery.getAreaTo())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(circleRoot.get("area"), findShapeQuery.getAreaTo()));
        }
        if (Objects.nonNull(findShapeQuery.getPerimeterFrom())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(circleRoot.get("perimeter"), findShapeQuery.getPerimeterFrom()));
        }
        if (Objects.nonNull(findShapeQuery.getPerimeterTo())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(circleRoot.get("perimeter"), findShapeQuery.getPerimeterTo()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getPredicateSquare(FindShapeQuery findShapeQuery, Root<Square> squareRoot) {
        Set<Predicate> predicates = new HashSet<>();
        if (Objects.nonNull(findShapeQuery.getCreatedBy())) {
            predicates.add(criteriaBuilder.equal(squareRoot.get("createdBy"), findShapeQuery.getCreatedBy()));
        }
        if (Objects.nonNull(findShapeQuery.getType())) {
            predicates.add(criteriaBuilder.like(squareRoot.get("type"), "%" + findShapeQuery.getType() + "%"));
        }
        if (Objects.nonNull(findShapeQuery.getParameterFrom())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(squareRoot.get("side"), findShapeQuery.getParameterFrom()));
        }
        if (Objects.nonNull(findShapeQuery.getParameterTo())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(squareRoot.get("side"), findShapeQuery.getParameterTo()));
        }
        if (Objects.nonNull(findShapeQuery.getAreaFrom())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(squareRoot.get("area"), findShapeQuery.getAreaFrom()));
        }
        if (Objects.nonNull(findShapeQuery.getAreaTo())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(squareRoot.get("area"), findShapeQuery.getAreaTo()));
        }
        if (Objects.nonNull(findShapeQuery.getPerimeterFrom())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(squareRoot.get("perimeter"), findShapeQuery.getPerimeterFrom()));
        }
        if (Objects.nonNull(findShapeQuery.getPerimeterTo())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(squareRoot.get("perimeter"), findShapeQuery.getPerimeterTo()));
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
