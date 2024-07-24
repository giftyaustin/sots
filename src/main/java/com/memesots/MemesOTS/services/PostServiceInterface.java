package com.memesots.MemesOTS.services;

import java.util.List;

import com.memesots.MemesOTS.models.Post;

public interface PostServiceInterface {
    public List<Post> getFYP(Integer postID, List<Integer> seenPostIDs);    
    public List<Post> getRelatedPosts(Integer postID, List<Integer> seenPostIDs);    
}
