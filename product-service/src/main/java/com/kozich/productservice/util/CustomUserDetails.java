package com.kozich.productservice.util;

import com.kozich.projectrepository.core.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final UserDTO userDTO;
    private final List<SimpleGrantedAuthority> simpleGrantedAuthorities;

    public CustomUserDetails(UserDTO userDTO, List<SimpleGrantedAuthority> simpleGrantedAuthorities) {
        this.userDTO = userDTO;
        this.simpleGrantedAuthorities = simpleGrantedAuthorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return simpleGrantedAuthorities;
    }

    public String getPassword() {
        return userDTO.getPassword();
    }

    public String getUsername() {
        return userDTO.getUuid().toString();
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
