package com.memesots.MemesOTS.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.memesots.MemesOTS.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
