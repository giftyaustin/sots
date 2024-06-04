package com.memesots.MemesOTS.filters;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("=================");
        System.out.println("=               =");
        System.out.println("= INside filter =");
        System.out.println("=               =");
        System.out.println("=               =");
        System.out.println("=================");
        System.out.println("Request: " + request.getRequestURI());
        log.error("ALREADY_FILTERED_SUFFIX", 1);
        filterChain.doFilter(request, response);
    }

}
