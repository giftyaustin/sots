package com.memesots.MemesOTS.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.memesots.MemesOTS.ExceptionHandlers.HttpException;
import com.memesots.MemesOTS.dao.PostRepository;
import com.memesots.MemesOTS.imageUploadService.ImageUploader;
import com.memesots.MemesOTS.imageUploadService.ImageUploader.ImageUploaderResponse;
import com.memesots.MemesOTS.lib.AppResponse;
import com.memesots.MemesOTS.models.Post;
import com.memesots.MemesOTS.models.User;
import com.memesots.MemesOTS.security.AppUserDetails;
import com.memesots.MemesOTS.services.PostServiceV1;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
public class PostController {

    @Autowired
    private PostServiceV1 postService;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    ImageUploader imageUploader;

    @GetMapping("/get-posts")
    public ResponseEntity<Map<String, Object>> getPosts(HttpServletResponse response) throws HttpException {
        List<Post> posts = postRepository.findPostsSortByLikes();
        return AppResponse.success(posts).toResponseEntity();
    }

    @PostMapping("/api/v1/get-posts/{post_id}")
    public ResponseEntity<Map<String, Object>> getPostFYP(@PathVariable("post_id") Integer postID,
            @Valid @RequestBody GetFYPSchema body,
            HttpServletResponse response) throws HttpException {
        List<Post> posts = postService.getFYP(postID, body.getSeenPostIDs());
        return AppResponse.success(posts).toResponseEntity();
    }

    @PostMapping("/add-post")
    public ResponseEntity<Map<String, Object>> addPost(@Valid @RequestBody AddPostSchemaDTO post) {
        Post newPost = new Post();
        Map<String, Object> response = new HashMap<>();
        try {
            String base64 = post.getBase64Image();

            // Get the current user
            AppUserDetails currUser = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            ImageUploaderResponse imageUploaderResponse = imageUploader.uploadImage(base64, "test-folder");
            newPost.setImgUrl(imageUploaderResponse.getImg_url());
            newPost.setImgIdentifier(imageUploaderResponse.getImg_identifier());
            newPost.setImgStorageCloudService("cloudinary");
            newPost.setPostCategory(post.getPost_category());
            newPost.setPostCaption(post.getPost_caption());
            newPost.setPostTags(post.getPost_tags());
            User user = new User();
            user.setId(currUser.getId());
            user.setUsername(currUser.getUsername());
            user.setEmail(currUser.getEmail());
            user.setPassword(currUser.getPassword());
            newPost.setUserID(user);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", false);
            response.put("message", "Failed to upload image");
            response.put("status_code", 500);
            return new ResponseEntity<>(response, null, 500);
        }
        postRepository.save(newPost);
        response.put("status", true);
        response.put("message", "Post added");
        response.put("status_code", 200);
        return new ResponseEntity<>(response, null, 200);
    }

    @Data
    public static class AddPostSchemaDTO {
        @NotBlank(message = "base64 image cannot be empty")
        private String base64Image;
        private String post_category;
        private String post_caption;
        private List<String> post_tags;
    }

    @Data
    public static class GetFYPSchema {
        @NotNull(message = "seenPostIDs cannot be null")
        private List<Integer> seenPostIDs;
    }

}
