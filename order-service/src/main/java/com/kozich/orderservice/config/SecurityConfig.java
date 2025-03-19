package com.kozich.orderservice.config;


import com.kozich.orderservice.controller.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter filter) throws Exception {

        http = http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable);

        http = http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http = http
                .exceptionHandling(eh -> eh.authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.setStatus(
                                    HttpServletResponse.SC_UNAUTHORIZED
                            );
                        }
                ).accessDeniedHandler((request, response, ex) -> {
                    response.setStatus(
                            HttpServletResponse.SC_FORBIDDEN
                    );
                }));

        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.GET, "/order/{uuid}").authenticated()
                .requestMatchers(HttpMethod.GET, "/order").authenticated()
                .requestMatchers(HttpMethod.GET, "/order/user/{uuid}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/order").authenticated()
                .anyRequest().authenticated()
        );


        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

}