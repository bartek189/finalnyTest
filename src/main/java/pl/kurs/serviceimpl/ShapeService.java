//package pl.kurs.serviceimpl;
//
//
//import com.querydsl.core.types.dsl.BooleanExpression;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import pl.kurs.entity.model.Shape;
//import pl.kurs.repository.ShapeRepository;
//import pl.kurs.util.SearchCriteria;
//import pl.kurs.util.ShapeUtils;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ShapeService extends BaseService {
//
//    private final ShapeUtils pu;
//
//    private final ShapeRepository pr;
//
//    public String getPostListing(String[] filter) {
//        List<SearchCriteria> criteria = formatSearchCriteria(filter);
//        System.out.println(criteria);
//        BooleanExpression exp = pu.getPCQFilterExp(criteria);
//        System.out.println(exp);
//        System.out.println(pr.findAll(exp));
//
//        String a = pr.findAll(exp).toString();
//
//
//        return a;
//    }
//
//}
