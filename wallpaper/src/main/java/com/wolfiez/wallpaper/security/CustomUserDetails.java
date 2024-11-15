package com.wolfiez.wallpaper.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;
    private final boolean accountNonExpired = true;
    private final boolean accountNonLocked = true;
    private final boolean credentialsNonExpired = true;
    private final boolean enabled = true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}