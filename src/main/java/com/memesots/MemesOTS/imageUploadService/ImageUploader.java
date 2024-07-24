package com.memesots.MemesOTS.imageUploadService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;

import lombok.Getter;
import lombok.Setter;


@Service
public class ImageUploader {
    @Autowired
    private Cloudinary cloudinary;

    public ImageUploaderResponse uploadImage(String image, String folderName) throws IOException {
        Map<String, Object> cloudinaryOptions = new HashMap<>();
        cloudinaryOptions.put("folder", folderName);
        cloudinaryOptions.put("overwrite", "true");
        cloudinaryOptions.put("secure", true);
        var response = cloudinary.uploader().upload(image, cloudinaryOptions);
        ImageUploaderResponse imageUploaderResponse = new ImageUploaderResponse();
        imageUploaderResponse.setImg_url(response.get("url").toString());
        imageUploaderResponse.setImg_identifier(response.get("public_id").toString());
        return imageUploaderResponse;
    }


    @Getter
    @Setter
    public static class ImageUploaderResponse {
        private String img_url;
        private String img_identifier;
        
    }
}
