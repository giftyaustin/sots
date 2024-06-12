package com.memesots.MemesOTS.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Autowired
    AppUserDetailsService appUserDetailsService;

    @Bean
    public UserDetailsService userDetailsService() {
        return appUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login", "/register", "/get-posts", "/google-signin").permitAll()
                    .anyRequest().authenticated()
                    )
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
                

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public AuthenticationProvider authenticationProvider() {
    //     DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    //     authenticationProvider.setUserDetailsService(userDetailsService());
    //     authenticationProvider.setPasswordEncoder(passwordEncoder());
    //     return authenticationProvider;

    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}