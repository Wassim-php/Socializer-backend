package com.example.javatech.user.service;

import com.example.javatech.global.response.ApiResponse;
import com.example.javatech.user.dto.UserCreateDTO;
import com.example.javatech.user.dto.UserDTO;
import com.example.javatech.user.dto.UserUpdateDTO;

import java.util.List;

public interface UserService {
    ApiResponse<UserDTO> create(UserCreateDTO userDTO);

    ApiResponse<List<UserDTO>> getAll();

    ApiResponse<UserDTO> getById(Long id);

    ApiResponse<UserDTO> update(Long id, UserUpdateDTO userDTO);

    ApiResponse<Void> delete(Long id);
}
