package com.kozich.userservice.util;

import com.kozich.userservice.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;
    private final List<SimpleGrantedAuthority> simpleGrantedAuthorities;

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
        simpleGrantedAuthorities = List.of(new SimpleGrantedAuthority(userEntity.getRole().name()));
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return simpleGrantedAuthorities;
    }

    public String getPassword() {
        return userEntity.getPassword();
    }

    public String getUsername() {
        return userEntity.getUuid().toString();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
