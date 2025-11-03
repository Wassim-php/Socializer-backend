package com.example.javatech.tag.dto;

import com.example.javatech.post.dto.PostDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TagDTO {
    private Long id;
    private String name;
    private List<PostDTO> posts;

}
