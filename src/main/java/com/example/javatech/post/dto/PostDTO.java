package com.example.javatech.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    private Long userId;
    private Long tagId;

    private List<Long> commentIds;
    private Set<Long> likedUserIds;

}
