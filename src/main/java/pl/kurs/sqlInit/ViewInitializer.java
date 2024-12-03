package pl.kurs.sqlInit;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@RequiredArgsConstructor
public class ViewInitializer {
    private final DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ClassPathResource resource = new ClassPathResource("data.sql");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            StringBuilder sqlContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlContent.append(line).append("\n");
            }
            statement.execute(sqlContent.toString());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}

