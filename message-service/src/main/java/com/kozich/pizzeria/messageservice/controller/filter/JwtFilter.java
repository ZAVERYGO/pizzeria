package com.kozich.pizzeria.messageservice.controller.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kozich.pizzeria.messageservice.controller.exceptionHandler.ErrorResponse;
import com.kozich.pizzeria.messageservice.controller.feign.client.UserFeignClient;
import com.kozich.pizzeria.messageservice.util.CustomUserDetails;
import com.kozich.pizzeria.messageservice.util.JwtTokenHandler;
import com.kozich.projectrepository.core.dto.UserDTO;
import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserFeignClient userManager;
    private final JwtTokenHandler jwtHandler;

    public JwtFilter(UserFeignClient userManager, JwtTokenHandler jwtHandler) {
        this.userManager = userManager;
        this.jwtHandler = jwtHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        if (!jwtHandler.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        UserDTO myCabinet;

        try {
            myCabinet = userManager.getUserById(jwtHandler.getUsername(token));
        } catch (FeignException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json; charset=UTF-8");


            ErrorResponse errorResponse = new ErrorResponse()
                    .setLogref("error")
                    .setMessage("Сервер не смог корректно обработать запрос. Пожалуйста обратитесь к администратору");


            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        }

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(myCabinet.getRole().name()));

        CustomUserDetails userDetails = new CustomUserDetails(myCabinet);

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                        List.of() : userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}