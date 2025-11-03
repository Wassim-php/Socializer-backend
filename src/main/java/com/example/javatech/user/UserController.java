package com.example.javatech.user;

import com.example.javatech.global.response.ApiResponse;
import com.example.javatech.user.dto.UserCreateDTO;
import com.example.javatech.user.dto.UserDTO;
import com.example.javatech.user.dto.UserUpdateDTO;
import com.example.javatech.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserDTO>> create(@Valid @RequestBody UserCreateDTO userCreateDTO){
        return  ResponseEntity.ok(userService.create(userCreateDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO){
        return ResponseEntity.ok(userService.update(id, userUpdateDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){
        return ResponseEntity.ok(userService.delete(id));
    }
}
