package com.example.javatech.tag;

import com.example.javatech.global.response.ApiResponse;
import com.example.javatech.tag.dto.TagCreateDTO;
import com.example.javatech.tag.dto.TagDTO;
import com.example.javatech.tag.dto.TagUpdateDTO;
import com.example.javatech.tag.service.TagService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<TagDTO>> createTag(@Valid @RequestBody TagCreateDTO tagDTO) {
        return ResponseEntity.ok(tagService.create(tagDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<TagDTO>>> getAllTags(){
        return ResponseEntity.ok(tagService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TagDTO>> getTagById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<TagDTO>> updateTag(@PathVariable Long id, @RequestBody
    TagUpdateDTO tagUpdateDTO) {
        return ResponseEntity.ok(tagService.update(id, tagUpdateDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTag(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.delete(id));
    }
}
