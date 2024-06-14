package com.memesots.MemesOTS.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;



@Data
public class GoogleSigninDTO {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Pic is required")
    private String profilePic;
}
