package com.memesots.MemesOTS.imageUploadService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;


@Service
public class ImageUploader {
    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(String image, String folderName) throws IOException {
        Map<String, Object> cloudinaryOptions = new HashMap<>();
        cloudinaryOptions.put("folder", folderName);
        cloudinaryOptions.put("overwrite", "true");
        cloudinaryOptions.put("secure", true);
        return cloudinary.uploader().upload(image, cloudinaryOptions).get("url").toString();
    }
}
