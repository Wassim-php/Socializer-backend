package com.example.javatech.post;

import com.example.javatech.comment.dto.CommentCreateDTO;
import com.example.javatech.global.response.ApiResponse;
import com.example.javatech.post.dto.PostCreateDTO;
import com.example.javatech.post.dto.PostDTO;
import com.example.javatech.post.dto.PostUpdateDTO;
import com.example.javatech.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PostDTO>> create(@Valid @RequestBody PostCreateDTO postCreateDTO){
        return  ResponseEntity.ok(postService.create(postCreateDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getAll(){
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> getById(@Valid @PathVariable Long id){
        return ResponseEntity.ok(postService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> update(@Valid @PathVariable Long id, @Valid @RequestBody PostUpdateDTO postUpdateDTO){
        return ResponseEntity.ok(postService.update(id, postUpdateDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@Valid @PathVariable Long id){
        return ResponseEntity.ok(postService.delete(id));
    }

    @PostMapping("/likePost/{id}")
    public ResponseEntity<ApiResponse<Void>> likePost(@Valid @PathVariable Long id){
        return ResponseEntity.ok(postService.likePost(id));
    }

    @PostMapping("unlikePost/{id}")
    public ResponseEntity<ApiResponse<Void>> unlikePost(@Valid @PathVariable Long id){
        return ResponseEntity.ok(postService.unlikePost(id));
    }

    @PostMapping("{id}/addComment")
    public ResponseEntity<ApiResponse<Void>> addComment(@Valid @PathVariable Long id, @Valid @RequestBody CommentCreateDTO commentCreateDTO){
        return ResponseEntity.ok(postService.addComment(id, commentCreateDTO));
    }
}
