package com.example.javatech.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostCreateDTO {
    private String content;
    private String imageUrl;

    private Long userId;
    private Long tagId;
}
