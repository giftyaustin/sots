package com.memesots.MemesOTS.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.memesots.MemesOTS.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p FROM Post p ORDER BY p.likes DESC, p.dislikes asc")
    public List<Post> findPostsSortByLikes();


    @Query("select p from Post p where p.id != :postID and p.id not in :seenPostIDs")
    public List<Post> findRelatedPosts(Integer postID, List<Integer> seenPostIDs);
}
