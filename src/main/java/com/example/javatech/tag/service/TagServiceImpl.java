package com.example.javatech.tag.service;

import com.example.javatech.comment.Comment;
import com.example.javatech.global.exception.InvalidRequestException;
import com.example.javatech.global.exception.ResourceNotFoundException;
import com.example.javatech.global.response.ApiResponse;
import com.example.javatech.post.Post;
import com.example.javatech.post.dto.PostDTO;
import com.example.javatech.tag.Tag;
import com.example.javatech.tag.TagRepository;
import com.example.javatech.tag.dto.TagCreateDTO;
import com.example.javatech.tag.dto.TagDTO;
import com.example.javatech.tag.dto.TagUpdateDTO;
import com.example.javatech.user.User;
import com.example.javatech.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    private TagDTO mapTo(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());

        List<PostDTO> postDTOs = tag.getPosts() != null
                ? tag.getPosts().stream()
                .map(post -> {
                    PostDTO dto = new PostDTO();
                    dto.setId(post.getId());
                    dto.setTagId(post.getTag() != null ? post.getTag().getId() : null);
                    dto.setContent(post.getContent());
                    dto.setImageUrl(post.getImageUrl());
                    dto.setCreatedAt(post.getCreatedAt());

                    if (post.getUser() != null) {
                        dto.setUserId(post.getUser().getId());
                    }

                    if (post.getLikedUsers() != null) {
                        dto.setLikedUserIds(
                                post.getLikedUsers().stream()
                                        .map(User::getId)
                                        .collect(Collectors.toSet())
                        );
                    }

                    if (post.getComments() != null) {
                        dto.setCommentIds(
                                post.getComments().stream()
                                        .map(Comment::getId)
                                        .collect(Collectors.toList())
                        );
                    }

                    return dto;
                })
                .collect(Collectors.toList())
                : new ArrayList<>();

        tagDTO.setPosts(postDTOs);
        return tagDTO;
    }

    public Tag mapFrom(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());

        if (tagDTO.getPosts() != null) {
            List<Post> posts = tagDTO.getPosts().stream()
                    .map(dto -> {
                        Post post = new Post();
                        post.setId(dto.getId());
                        post.setContent(dto.getContent());
                        post.setImageUrl(dto.getImageUrl());
                        post.setCreatedAt(dto.getCreatedAt());
                        post.setTag(tag);

                        if (dto.getUserId() != null) {
                            User user = new User();
                            user.setId(dto.getUserId());
                            post.setUser(user);
                        }

                        if (dto.getLikedUserIds() != null) {
                            Set<User> likedUsers = dto.getLikedUserIds().stream()
                                    .map(id -> {
                                        User u = new User();
                                        u.setId(id);
                                        return u;
                                    })
                                    .collect(Collectors.toSet());
                            post.setLikedUsers(likedUsers);
                        }

                        if (dto.getCommentIds() != null) {
                            List<Comment> comments = dto.getCommentIds().stream()
                                    .map(id -> {
                                        Comment c = new Comment();
                                        c.setId(id);
                                        return c;
                                    })
                                    .collect(Collectors.toList());
                            post.setComments(comments);
                        }

                        return post;
                    })
                    .collect(Collectors.toList());

            tag.setPosts(posts);
        }

        return tag;
    }

    @Override
    public ApiResponse<TagDTO> create(TagCreateDTO tagDTO) {
        Tag tag = new Tag();
        tag.setName(tagDTO.getName());

        TagDTO result = mapTo(tagRepository.save(tag));
        return new ApiResponse<>("Tag created successfully!", result, true);
    }

    @Override
    public ApiResponse<List<TagDTO>> getAll() {
        List<Tag> tags = tagRepository.findAll();
        List<TagDTO> dtos = tags.stream().map(this::mapTo).toList();
        return new ApiResponse<>("Tags retrieved successfully!", dtos, true);
    }

    @Override
    public ApiResponse<TagDTO> getById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag with id " + id + " not found"));

        return new ApiResponse<>("Tag found successfully!", mapTo(tag), true);
    }

    @Override
    public ApiResponse<TagDTO> update(Long id, TagUpdateDTO dto) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag with id " + id + " not found"));

        tag.setName(dto.getName());
        TagDTO result = mapTo(tagRepository.save(tag));
        return new ApiResponse<>("Tag updated successfully!", result, true);
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag with id " + id + " not found"));

        if (tag.getPosts() != null && !tag.getPosts().isEmpty()) {
            throw new InvalidRequestException("Cannot delete tag because it is used in existing posts.");
        }

        tagRepository.delete(tag);
        return new ApiResponse<>("Tag deleted successfully!", null, true);
    }
}
