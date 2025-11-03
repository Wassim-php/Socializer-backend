package com.example.javatech.comment.service;

import com.example.javatech.comment.dto.CommentCreateDTO;
import com.example.javatech.comment.dto.CommentDTO;
import com.example.javatech.comment.dto.CommentUpdateDTO;
import com.example.javatech.global.response.ApiResponse;

import java.util.List;

public interface CommentService {
    ApiResponse<CommentDTO> create(CommentCreateDTO commentDTO);

    ApiResponse<List<CommentDTO>> getAll();

    ApiResponse<CommentDTO> getById(Long id);

    ApiResponse<CommentDTO> update(Long id, CommentUpdateDTO commentUpdateDTO);

    ApiResponse<Void> delete(Long id);

    ApiResponse<List<CommentDTO>> getCommentsByPostId(Long postId);
}
