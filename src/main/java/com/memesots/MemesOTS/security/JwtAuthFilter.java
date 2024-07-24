package com.memesots.MemesOTS.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    AppUserDetailsService appUserDetailsService;

    private static final List<String> EXCLUDED_URLS = List.of("/login", "/register", "/google-signin", "/get-posts");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        Map<String, Object> responseBody = new HashMap<>();

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                responseBody.put("status", false);
                responseBody.put("status_code", 401);
                responseBody.put("message", "JWT not found");
                response.setStatus(401);
                String resString = mapper.writeValueAsString(responseBody);
                response.getWriter().write(resString);
                return;
            }
            token = authHeader.substring(7);
            email = jwtService.extractEmail(token);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                AppUserDetails userDetails = (AppUserDetails) appUserDetailsService.loadUserByEmail(email);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                    return;
                }
                else{
                    responseBody.put("status", false);
                    responseBody.put("status_code", 401);
                    responseBody.put("message", "Invalid JWT");
                    response.setStatus(401);
                    String resString = mapper.writeValueAsString(responseBody);
                    response.getWriter().write(resString);
                    return;
                }
            }
            else{
                responseBody.put("status", false);
                responseBody.put("status_code", 401);
                responseBody.put("message", "Invalid JWT");
                response.setStatus(401);
                String resString = mapper.writeValueAsString(responseBody);
                response.getWriter().write(resString);
                return;
            }

        } catch (MalformedJwtException | SignatureException | ExpiredJwtException e) {
            responseBody.put("status", false);
            responseBody.put("status_code", 401);
            responseBody.put("message", "Invalid token");
            response.setStatus(401);
            String resString = mapper.writeValueAsString(responseBody);
            response.getWriter().write(resString);
            return;
        }

        catch (Exception e) {
            System.out.println(e.getClass().getName() + "====================");
            responseBody.put("status", false);
            responseBody.put("status_code", 500);
            responseBody.put("message", "INTERNAL SERVER ERROR");
            response.setStatus(401);
            String resString = mapper.writeValueAsString(responseBody);
            response.getWriter().write(resString);
            return;
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDED_URLS.contains(path);
    }
}