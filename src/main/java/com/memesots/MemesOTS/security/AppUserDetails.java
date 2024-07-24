package com.memesots.MemesOTS.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;


@Data
public class AppUserDetails implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private String email;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

  
    
}
