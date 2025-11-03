package com.example.javatech.comment;

import com.example.javatech.comment.dto.CommentCreateDTO;
import com.example.javatech.comment.dto.CommentDTO;
import com.example.javatech.comment.dto.CommentUpdateDTO;
import com.example.javatech.comment.service.CommentService;
import com.example.javatech.global.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CommentDTO>> create(@Valid @RequestBody CommentCreateDTO commentCreateDTO){
        return ResponseEntity.ok(commentService.create(commentCreateDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> findAll(){
        return ResponseEntity.ok(commentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentDTO>> getById(@PathVariable Long id){
        return ResponseEntity.ok(commentService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CommentDTO>> update(@Valid @PathVariable Long id, @RequestBody CommentUpdateDTO commentUpdateDTO){
        return ResponseEntity.ok(commentService.update(id, commentUpdateDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@Valid @PathVariable Long id){
        return ResponseEntity.ok(commentService.delete(id));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getAll(@PathVariable Long id){
        return ResponseEntity.ok(commentService.getCommentsByPostId(id));
    }
}
