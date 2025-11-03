package com.example.javatech.post.service;

import com.example.javatech.comment.dto.CommentCreateDTO;
import com.example.javatech.global.response.ApiResponse;
import com.example.javatech.post.dto.PostCreateDTO;
import com.example.javatech.post.dto.PostDTO;
import com.example.javatech.post.dto.PostUpdateDTO;

import java.util.List;

public interface PostService {
    ApiResponse<PostDTO> create(PostCreateDTO postCreateDTO);
    ApiResponse<List<PostDTO>> getAll();
    ApiResponse<PostDTO> getById(Long id);
    ApiResponse<PostDTO> update(Long id, PostUpdateDTO postUpdateDTO);
    ApiResponse<Void> delete(Long id);
    ApiResponse<Void> likePost(Long id);
    ApiResponse<Void> unlikePost(Long id);
    ApiResponse<Void> addComment(Long id, CommentCreateDTO commentCreateDTO);
}
