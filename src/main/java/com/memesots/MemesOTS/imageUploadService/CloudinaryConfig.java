package com.memesots.MemesOTS.imageUploadService;
// Import the required packages

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    Dotenv dotenv = Dotenv.load();
    // Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
    // cloudinary.config.secure = true;

    @Bean
    public Cloudinary cloudinary (){
        return new Cloudinary(dotenv.get("CLOUDINARY_URL"));
    }
}
