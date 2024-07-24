package com.memesots.MemesOTS.security;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {
    
    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthRegistration(JwtAuthFilter jwtAuthFilter){
        FilterRegistrationBean<JwtAuthFilter> regBean = new FilterRegistrationBean<>();
        regBean.setFilter(jwtAuthFilter);
        return regBean;
    }
}
