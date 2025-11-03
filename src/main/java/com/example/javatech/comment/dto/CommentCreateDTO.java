package com.example.javatech.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CommentCreateDTO {
    @NotBlank(message = "Content Cannot be Blank")
    String content;
    LocalDateTime createdAt;
}
