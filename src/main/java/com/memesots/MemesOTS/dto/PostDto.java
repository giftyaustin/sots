package com.memesots.MemesOTS.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String postCategory;

    @NotBlank
    private String imgUrl;

    private String postCaption;

    private List<String> postTags;

}
