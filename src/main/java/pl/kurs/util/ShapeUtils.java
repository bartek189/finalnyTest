package pl.kurs.util;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kurs.entity.model.QSquare;
import pl.kurs.entity.model.Shape;
import pl.kurs.predicate.CommonPredicateBuilder;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShapeUtils {

    public BooleanExpression getPCQFilterExp(List<SearchCriteria> criterias) {

        BooleanExpression exp = new CommonPredicateBuilder<>(Shape.class).build();
        QSquare s = QSquare.square;
        for (SearchCriteria criteria : criterias) {

            exp = exp.and(new CommonPredicateBuilder<>(Shape.class).and(criteria).build());

        }
        return exp;
    }


}
