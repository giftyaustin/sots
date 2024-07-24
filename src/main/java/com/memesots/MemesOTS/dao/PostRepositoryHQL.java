package com.memesots.MemesOTS.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.memesots.MemesOTS.models.Post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;



@Repository
public class PostRepositoryHQL {
    @Autowired
    private EntityManager entityManager;

    public List<Post> findRelatedPosts(Integer postID, List<Integer> seenPostIDs) {
        TypedQuery<Post> query = entityManager.createQuery("select p from Post p where p.id != :postID and p.id not in :seenPostIDs", Post.class);
        return 
        query
        .setParameter("postID", postID)
        .setParameter("seenPostIDs", seenPostIDs)
        .setMaxResults(1)
        .getResultList();
    }

}
