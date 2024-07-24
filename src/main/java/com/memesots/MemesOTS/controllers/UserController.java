package com.memesots.MemesOTS.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.memesots.MemesOTS.ExceptionHandlers.AppException;
import com.memesots.MemesOTS.ExceptionHandlers.AppException.UserNotFoundException;
import com.memesots.MemesOTS.lib.AppResponse;
import com.memesots.MemesOTS.lib.enums.RC;
import com.memesots.MemesOTS.lib.enums.SignupServices;
import com.memesots.MemesOTS.models.User;
import com.memesots.MemesOTS.security.JwtService;
import com.memesots.MemesOTS.services.UserService;
import com.memesots.MemesOTS.services.UserService.GetUserDTO;
import com.memesots.MemesOTS.socialLogin.GoogleProfile;
import com.memesots.MemesOTS.socialLogin.GoogleProfileService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Data;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;
    @Autowired
    private GoogleProfileService googleProfileService;
   

    @PostMapping("/google-signin")
    public ResponseEntity<Map<String, Object>> googleAuthentication(@Valid @RequestBody GoogleSigninDTO user, HttpServletResponse res)
            throws AppException {
        String accessToken = user.getToken();
        GoogleProfile googleProfile = googleProfileService.getProfile(accessToken);
        String email = googleProfile.getEmailAddresses().get(0).getValue();
        String profilePic = googleProfile.getPhotos().get(0).getUrl();
        String username = googleProfile.getNames().get(0).getUnstructuredName();
        boolean doesUserExist = userService.doesUserExist(email);
        String token = jwtService.generateToken(email);
        Cookie cookie = new Cookie("mmlt", token);
        res.addCookie(cookie);

        if (doesUserExist) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("status", true);
            response.put("message", "Logging in");
            response.put("response_code", RC.SUCCESS);
            return new ResponseEntity<>(response, null, 200);
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setProfile_pic(profilePic);
        newUser.setSignup_service(SignupServices.GOOGLE);
        userService.createUser(newUser);


        // Build the response
        HashMap<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("status", true);
        response.put("message", "New user added");
        response.put("response_code", RC.SUCCESS);
        return new ResponseEntity<>(response, null, 200);
    }

    @Data
    private static class GoogleSigninDTO {
        private String token;
    }



    @GetMapping("/api/v1/user/{user_email}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable("user_email") String email) throws AppException {
        GetUserDTO userInfo = userService.getUser(email);
        if(userInfo == null){
            throw new AppException(false, 404, "User not found", RC.USER_NOT_FOUND);
        }
        return AppResponse.success(userInfo).toResponseEntity();
    }
}
