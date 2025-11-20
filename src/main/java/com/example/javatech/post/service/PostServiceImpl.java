package com.example.javatech.post.service;

import com.example.javatech.comment.Comment;
import com.example.javatech.comment.CommentRepository;
import com.example.javatech.comment.dto.CommentCreateDTO;
import com.example.javatech.global.exception.ResourceNotFoundException;
import com.example.javatech.global.exception.UnauthorizedActionException;
import com.example.javatech.global.response.ApiResponse;
import com.example.javatech.post.Post;
import com.example.javatech.post.PostRepository;
import com.example.javatech.post.dto.PostCreateDTO;
import com.example.javatech.post.dto.PostDTO;
import com.example.javatech.post.dto.PostUpdateDTO;
import com.example.javatech.tag.service.TagServiceImpl;
import com.example.javatech.user.User;
import com.example.javatech.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private CommentRepository commentRepository;

    private PostDTO mapTo(Post post){
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setImageUrl(post.getImageUrl());
        dto.setCreatedAt(post.getCreatedAt());

        if (post.getUser() != null)
            dto.setUserId(post.getUser().getId());

        if (post.getTag() != null)
            dto.setTagId(post.getTag().getId());

        if (post.getComments() != null) {
            dto.setCommentIds(
                    post.getComments()
                            .stream()
                            .map(Comment::getId)
                            .collect(Collectors.toList())
            );
        }

        if (post.getLikedUsers() != null) {
            dto.setLikedUserIds(
                    post.getLikedUsers()
                            .stream()
                            .map(User::getId)
                            .collect(Collectors.toSet())
            );
        }

        return dto;
    }

    @Override
    public ApiResponse<PostDTO> create(PostCreateDTO postCreateDTO){
        User user = userService.getCurrentUser();

        Post post = new Post();
        post.setContent(postCreateDTO.getContent());
        post.setImageUrl(postCreateDTO.getImageUrl());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        post.setTag(
                tagService.mapFrom(
                        tagService.getById(postCreateDTO.getTagId()).getData()
                )
        );

        PostDTO dto = mapTo(postRepository.save(post));
        return new ApiResponse<>("Post has been created successfully!", dto, true);
    }

    @Override
    public ApiResponse<List<PostDTO>> getAll(){
        List<PostDTO> posts = postRepository.findAll()
                .stream().map(this::mapTo)
                .collect(Collectors.toList());

        return new ApiResponse<>("Posts fetched successfully!", posts, true);
    }

    @Override
    public ApiResponse<PostDTO> getById(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post with id " + id + " not found"));

        return new ApiResponse<>("Post fetched successfully!", mapTo(post), true);
    }

    @Override
    public ApiResponse<PostDTO> update(Long id, PostUpdateDTO postUpdateDTO){
        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post with id " + id + " not found"));

        post.setContent(postUpdateDTO.getContent());
        post.setImageUrl(postUpdateDTO.getImageUrl());
        post.setTag(
                tagService.mapFrom(
                        tagService.getById(postUpdateDTO.getTagId()).getData()
                )
        );

        PostDTO dto = mapTo(postRepository.save(post));
        return new ApiResponse<>("Post updated successfully!", dto, true);
    }

    @Override
    public ApiResponse<Void> delete(Long id){
        User user = userService.getCurrentUser();

        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post with id " + id + " not found"));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedActionException("You are not allowed to delete this post");
        }

        postRepository.delete(post);
        return new ApiResponse<>("Post deleted successfully!", null, true);
    }

    @Override
    public ApiResponse<Void> likePost(Long id) {
        User user = userService.getCurrentUser();

        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post with id " + id + " not found"));

        post.getLikedUsers().add(user);
        postRepository.save(post);

        return new ApiResponse<>("Post liked successfully!", null, true);
    }

    @Override
    public ApiResponse<Void> unlikePost(Long id) {
        User user = userService.getCurrentUser();

        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post with id " + id + " not found"));

        post.getLikedUsers().remove(user);
        postRepository.save(post);

        return new ApiResponse<>("Post unliked successfully!", null, true);
    }

    @Override
    public ApiResponse<Void> addComment(Long id, CommentCreateDTO commentCreateDTO) {
        User user = userService.getCurrentUser();

        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post with id " + id + " not found"));

        Comment comment = new Comment();
        comment.setContent(commentCreateDTO.getContent());
        comment.setCreatedAt(commentCreateDTO.getCreatedAt());
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);

        return new ApiResponse<>("Comment added successfully!", null, true);
    }
}
