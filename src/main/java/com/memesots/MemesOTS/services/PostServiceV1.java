package com.memesots.MemesOTS.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memesots.MemesOTS.dao.PostRepository;
import com.memesots.MemesOTS.dao.PostRepositoryHQL;
import com.memesots.MemesOTS.models.Post;

@Service
public class PostServiceV1 implements PostServiceInterface {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostRepositoryHQL postRepositoryV2;

    @Override
    public List<Post> getFYP(Integer postID, List<Integer> seenPostIDs) {
        Optional<Post> post = postRepository.findById(postID);
        if (!post.isPresent()) {
            return null;
        }
        List<Post> relatedPosts = this.getRelatedPosts(postID, seenPostIDs);
        List<Post> resultPosts = new ArrayList<>();
        resultPosts.add(post.get());
        resultPosts.addAll(relatedPosts);
        return resultPosts;
    }

    @Override
    public List<Post> getRelatedPosts(Integer postID, List<Integer> seenPostIDs) {
        List<Post> posts = postRepositoryV2.findRelatedPosts(postID, seenPostIDs);
        return posts;
    }

}
