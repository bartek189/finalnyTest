package pl.kurs.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Repository;
import pl.kurs.entity.model.FindShapeQuery;
import pl.kurs.entity.response.ShapeResponse;
import pl.kurs.shapeFactory.ShapeDtoFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShapeQueryRepository {

    private final EntityManager entityManager;
    private final ShapeDtoFactory shapeFactory;

    public List<ShapeResponse> findAllShape(FindShapeQuery query) throws SQLException {

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM SHAPEWITHPARAMETERS WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (query != null) {
            Class<? extends FindShapeQuery> queryClass = query.getClass();
            Field[] fields = queryClass.getDeclaredFields();

            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(query);

                    if (value != null) {
                        if (value instanceof String && !((String) value).isEmpty()) {
                            queryBuilder.append(" AND ").append(field.getName()).append(" = ?");
                            parameters.add(value);
                        } else if (value instanceof Number) {
                            if ("areaFrom".equals(field.getName())) {
                                queryBuilder.append(" AND AREA >= ?");
                                parameters.add(value);
                            } else if ("areaTo".equals(field.getName())) {
                                queryBuilder.append(" AND AREA <= ?");
                                parameters.add(value);
                            } else if ("perimeterFrom".equals(field.getName())) {
                                queryBuilder.append(" AND PERIMETER >= ?");
                                parameters.add(value);
                            } else if ("perimeterTo".equals(field.getName())) {
                                queryBuilder.append(" AND PERIMETER <= ?");
                                parameters.add(value);
                            } else {
                                queryBuilder.append(" AND ").append(field.getName()).append(" = ?");
                                parameters.add(value);
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new SQLException();
                }
            }
        }


        EntityManagerFactoryInfo info = (EntityManagerFactoryInfo) entityManager.getEntityManagerFactory();
        Connection connection = info.getDataSource().getConnection();
        PreparedStatement pstmt = connection.prepareStatement(queryBuilder.toString());

        for (int i = 0; i < parameters.size(); i++) {
            pstmt.setObject(i + 1, parameters.get(i));
        }

        ResultSet resultSet = pstmt.executeQuery();

        List<ShapeResponse> shapes = new ArrayList<>();

        while (resultSet.next()) {
            ShapeResponse shape = shapeFactory.getShapeDtoService(resultSet);
            shapes.add(shape);
        }

        return shapes;

    }
}

