package com.memesots.MemesOTS.controllers;


import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.memesots.MemesOTS.ExceptionHandlers.HttpException;
import com.memesots.MemesOTS.dao.PostRepository;
import com.memesots.MemesOTS.imageUploadService.ImageUploader;
import com.memesots.MemesOTS.models.Post;
import com.memesots.MemesOTS.services.PostService;

import lombok.Data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @Autowired ImageUploader imageUploader;

    @GetMapping("/get-posts")
    public List<Post> getPosts() throws HttpException {
        System.out.println("======  post controller ========");
        if(true){
            throw new HttpException("test exception");
        }
        return postRepository.findAll();
    }

    
    
    @PostMapping("/add-post")
    public ResponseEntity<AddPostResponseDTO> addPost(@RequestBody Map<String, String> post) {
        AddPostResponseDTO response = new AddPostResponseDTO();
        Post newPost = new Post();
        String cloudinaryImgUrl;
        try {
            String base64 = post.get("img_url");
           cloudinaryImgUrl =  imageUploader.uploadImage(base64, "test-folder");
           newPost.setImgUrl(cloudinaryImgUrl);
           newPost.setImgStorageCloudService("cloudinary");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(false);
            response.setMessage("Failed to upload image");
            return ResponseEntity.ok(response);
        }
        postRepository.save(newPost);
        response.setStatus(true);
        response.setMessage("Post added");
        return ResponseEntity.ok(response);
    }

}


@Data
class AddPostResponseDTO {
    private boolean status;
    private String message;
}

