package com.example.javatech.comment.service;

import com.example.javatech.comment.Comment;
import com.example.javatech.comment.CommentRepository;
import com.example.javatech.comment.dto.CommentCreateDTO;
import com.example.javatech.comment.dto.CommentDTO;
import com.example.javatech.comment.dto.CommentUpdateDTO;
import com.example.javatech.global.exception.ResourceNotFoundException;
import com.example.javatech.global.response.ApiResponse;
import com.example.javatech.user.User;
import com.example.javatech.user.UserRepository;
import com.example.javatech.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    public CommentDTO mapTo(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUserId(comment.getUser().getId());
        return dto;
    }

    public Comment mapFrom(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(commentDTO.getCreatedAt());

        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        comment.setUser(user);
        return comment;
    }

    @Override
    public ApiResponse<CommentDTO> create(CommentCreateDTO commentCreateDTO) {
        User user = userService.getCurrentUser();

        Comment comment = new Comment();
        comment.setContent(commentCreateDTO.getContent());
        comment.setCreatedAt(commentCreateDTO.getCreatedAt());
        comment.setUser(user);

        CommentDTO savedComment = mapTo(commentRepository.save(comment));
        return new ApiResponse<>("Comment created successfully!", savedComment, true);
    }

    @Override
    public ApiResponse<List<CommentDTO>> getAll() {
        List<CommentDTO> comments = commentRepository.findAll()
                .stream().map(this::mapTo).toList();

        return new ApiResponse<>("Comments fetched successfully!", comments, true);
    }

    @Override
    public ApiResponse<CommentDTO> getById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        return new ApiResponse<>("Comment found successfully!", mapTo(comment), true);
    }

    @Override
    public ApiResponse<CommentDTO> update(Long id, CommentUpdateDTO commentUpdateDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        comment.setContent(commentUpdateDTO.getContent());

        return new ApiResponse<>("Comment updated successfully!",
                mapTo(commentRepository.save(comment)), true);
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        commentRepository.delete(comment);

        return new ApiResponse<>("Comment deleted successfully!", null, true);
    }

    @Override
    public ApiResponse<List<CommentDTO>> getCommentsByPostId(Long postId) {
        List<CommentDTO> comments = commentRepository.getCommentsByPost_Id(postId)
                .stream().map(this::mapTo).toList();

        return new ApiResponse<>("Comments fetched successfully!", comments, true);
    }
}
