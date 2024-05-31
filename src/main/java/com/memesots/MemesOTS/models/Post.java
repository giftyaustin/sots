package com.memesots.MemesOTS.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name = "posts")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "post_category")
    private String postCategory;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @Column(name = "post_caption", columnDefinition = "TEXT")
    private String postCaption;

    @Column(name = "post_tags")
    private List<String> postTags;

    @Column(name = "img_storage_cloud_service", nullable = false)
    private String imgStorageCloudService;

    @Column(name = "img_identifier")
    private String imgIdentifier;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userID;
}
