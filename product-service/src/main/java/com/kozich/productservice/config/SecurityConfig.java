package com.kozich.productservice.config;


import com.kozich.productservice.controller.filter.JwtFilter;
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
                .requestMatchers(HttpMethod.GET, "/product/{uuid}").authenticated()
                .requestMatchers(HttpMethod.GET, "/product").authenticated()
                .requestMatchers(HttpMethod.GET, "/product/category/{uuid}").authenticated()
                .requestMatchers(HttpMethod.POST, "/product").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/{uuid}/dt_update/{dt_update}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/{uuid}/dt_update/{dt_update}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/category/{uuid}").authenticated()
                .requestMatchers(HttpMethod.GET, "/category").authenticated()
                .requestMatchers(HttpMethod.POST, "/category").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/{uuid}/dt_update/{dt_update}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/{uuid}/dt_update/{dt_update}").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
        );


        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

}