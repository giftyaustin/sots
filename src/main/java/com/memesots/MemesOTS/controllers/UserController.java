package com.memesots.MemesOTS.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.memesots.MemesOTS.security.JwtService;
import com.memesots.MemesOTS.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@RestController
@RequestMapping("/")
public class UserController {



    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Map<String, String> user) {
        String jwt = user.get("jwt");
        String username = jwtService.extractAllClaims(jwt).getSubject();
        userService.saveUser(username);
        return new ResponseEntity<>(Map.of("message", "User registered successfully"), null, 200);
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticateUser(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = user.get("password");
        boolean isValidUser = userService.validateUser(username, password);
        if (!isValidUser) {
            throw new RuntimeException("Invalid username/password");
        }
        String token = jwtService.generateToken(username);
        Map<String, Object> response = Map.of("token", token);
        return new ResponseEntity<>(response, null, 200);
    }
}
