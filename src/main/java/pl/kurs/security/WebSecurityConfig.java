package pl.kurs.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.kurs.config.AppConfig;


@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtUserDetailsService service;
    private final JwtRequestFilter filter;
    private final AppConfig appConfig;


    private static final String[] AUTH_LIST = {
            "/h2-console/**",
            "/authenticate",
            "/register/users"

    };

    @SneakyThrows
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.userDetailsService(service).passwordEncoder(appConfig.passwordEncoder());
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable().and()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_LIST)
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}



