import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@RequiredArgsConstructor
public class Config {
    private final DataSource dataSource;

    @PostConstruct
    public void init() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE VIEW  SHAPEWITHPARAMETERS AS SELECT DTYPE, ID, CREATED_AT,CREATED_BY,LAST_MODIFIED_AT,LAST_MODIFIED_BY,TYPE,VERSION,SIDE,WIDTH,HEIGHT,RADIUS,CASE WHEN TYPE = 'CIRCLE' THEN  PI() * RADIUS * RADIUS WHEN TYPE = 'RECTANGLE' THEN WIDTH * HEIGHT When TYPE = 'SQUARE' THEN SIDE*SIDE ELSE NULL END AS area, CASE WHEN TYPE = 'CIRCLE' THEN 2 * PI() * RADIUS WHEN TYPE = 'RECTANGLE' THEN 2 * (WIDTH + HEIGHT) WHEN TYPE = 'SQUARE' THEN 4 * SIDE ELSE NULL END AS perimeter FROM SHAPE");
        } catch (SQLException e) {
        }
    }
}
