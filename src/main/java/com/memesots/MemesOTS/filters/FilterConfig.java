package com.memesots.MemesOTS.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {


    @Bean
    FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AuthFilter());
        registration.addUrlPatterns("/*");
        registration.addUrlPatterns("/get-posts");
        registration.setOrder(10000);
        return registration;
    }
}
