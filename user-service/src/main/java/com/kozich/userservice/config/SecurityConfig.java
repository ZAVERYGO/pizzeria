package com.kozich.userservice.config;


import com.kozich.userservice.controller.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .requestMatchers("/users/**").hasAnyRole("ADMIN")
                .requestMatchers("/cabinet/registration").permitAll()
                .requestMatchers("/cabinet/verification").permitAll()
                .requestMatchers("/cabinet/login").permitAll()
                .requestMatchers("/cabinet/me").authenticated()
                .requestMatchers("/feign/*").permitAll()

                .anyRequest().authenticated()
        );


        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

}