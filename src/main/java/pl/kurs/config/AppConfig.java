package pl.kurs.config;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;

//@EnableSwagger2
@Configuration
@RequiredArgsConstructor
public class AppConfig {
    @Bean
    public ModelMapper createModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    private final DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE VIEW  SHAPEWITHPARAMETERS AS SELECT DTYPE, ID, CREATED_AT,CREATED_BY,LAST_MODIFIED_AT,LAST_MODIFIED_BY,TYPE,VERSION,SIDE,WIDTH,HEIGHT,RADIUS,CASE WHEN TYPE = 'CIRCLE' THEN  PI() * RADIUS * RADIUS WHEN TYPE = 'RECTANGLE' THEN WIDTH * HEIGHT When TYPE = 'SQUARE' THEN SIDE*SIDE ELSE NULL END AS area, CASE WHEN TYPE = 'CIRCLE' THEN 2 * PI() * RADIUS WHEN TYPE = 'RECTANGLE' THEN 2 * (WIDTH + HEIGHT) WHEN TYPE = 'SQUARE' THEN 4 * SIDE ELSE NULL END AS perimeter FROM SHAPE");
        } catch (SQLException e) {
        }
    }

}
