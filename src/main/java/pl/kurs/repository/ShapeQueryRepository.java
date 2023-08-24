package pl.kurs.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShapeQueryRepository {

    private final EntityManager entityManager;

    public List findAllShape() {
        Query query = entityManager.createNativeQuery(" select * FROM ShapeWithParameters", Query.class);

        return query.getResultList();
    }
}
