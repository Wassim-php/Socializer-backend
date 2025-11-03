package com.example.javatech.tag.service;

import com.example.javatech.global.response.ApiResponse;
import com.example.javatech.tag.dto.TagCreateDTO;
import com.example.javatech.tag.dto.TagDTO;
import com.example.javatech.tag.dto.TagUpdateDTO;

import java.util.List;

public interface TagService {
    ApiResponse<TagDTO> create(TagCreateDTO tagDTO);

    ApiResponse<List<TagDTO>> getAll();

    ApiResponse<TagDTO> getById(Long id);

    ApiResponse<TagDTO> update(Long id, TagUpdateDTO tagDTO);

    ApiResponse<Void> delete(Long id);

}
