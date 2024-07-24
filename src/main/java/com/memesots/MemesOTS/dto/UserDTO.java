package com.memesots.MemesOTS.dto;

import com.memesots.MemesOTS.lib.enums.SignupServices;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private SignupServices signup_service;
    private String profile_pic;
    private Integer id;
}
