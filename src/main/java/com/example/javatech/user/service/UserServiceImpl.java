package com.example.javatech.user.service;

import com.example.javatech.global.exception.ResourceNotFoundException;
import com.example.javatech.global.response.ApiResponse;
import com.example.javatech.user.User;
import com.example.javatech.user.UserRepository;
import com.example.javatech.user.dto.UserCreateDTO;
import com.example.javatech.user.dto.UserDTO;
import com.example.javatech.user.dto.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserDTO mapTo(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

    @Override
    public ApiResponse<UserDTO> create(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        return new ApiResponse<>("User created successfully!", mapTo(userRepository.save(user)), true);
    }

    @Override
    public ApiResponse<List<UserDTO>> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = users.stream().map(this::mapTo).toList();
        return new ApiResponse<>("Users fetched successfully!", userDTOS, true);
    }

    @Override
    public ApiResponse<UserDTO> getById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new ApiResponse<>("User fetched successfully!", mapTo(user), true);
    }

    @Override
    public ApiResponse<UserDTO> update(Long id, UserUpdateDTO userDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return new ApiResponse<>("User updated successfully!", mapTo(userRepository.save(user)), true);
    }

    @Override
    public ApiResponse<Void> delete(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);

        return new ApiResponse<>("User deleted successfully!", null, true);
    }

}
