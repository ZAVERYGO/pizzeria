package com.kozich.userservice.controller.filter;

import com.kozich.userservice.service.api.UserService;
import com.kozich.userservice.entity.UserEntity;
import com.kozich.userservice.util.JwtTokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtTokenHandler jwtHandler;

    public JwtFilter(UserService userService, JwtTokenHandler jwtHandler) {
        this.userService = userService;
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

        UserEntity userEntity = userService.getById(UUID.fromString(jwtHandler.getUsername(token)));

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(userEntity.getRole().name()));
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return simpleGrantedAuthorities;
            }

            @Override
            public String getPassword() {
                return userEntity.getPassword();
            }

            @Override
            public String getUsername() {
                return userEntity.getUuid().toString();
            }

            @Override
            public boolean isAccountNonExpired() {
                return UserDetails.super.isAccountNonExpired();
            }

            @Override
            public boolean isAccountNonLocked() {
                return UserDetails.super.isAccountNonLocked();
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return UserDetails.super.isCredentialsNonExpired();
            }

            @Override
            public boolean isEnabled() {
                return UserDetails.super.isEnabled();
            }
        };


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