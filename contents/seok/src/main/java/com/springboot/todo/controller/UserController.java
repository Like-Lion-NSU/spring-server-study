package com.springboot.todo.controller;

import com.springboot.todo.dto.UserRequestDTO;
import com.springboot.todo.dto.UserResponseDTO;
import com.springboot.todo.entity.User;
import com.springboot.todo.security.TokenProvider;
import com.springboot.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserController(UserService userService, TokenProvider tokenProvider){
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping
    public List<UserResponseDTO> retrieveUserList(){
        List<User> users = userService.retrieveAll();
        List<UserResponseDTO> dtos = users.stream().map(UserResponseDTO::new).collect(Collectors.toList());
        return dtos;
    }

    @PostMapping
    public UserResponseDTO registerUser(@RequestBody UserRequestDTO dto){
        User user = User.builder()
                .id(null)
                .userId(dto.getUserId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .userName(dto.getUserName())
                .build();
        User created = userService.create(user);
        UserResponseDTO response = new UserResponseDTO(created);
        return response;
    }

    @PostMapping("/login")
    public UserResponseDTO authenticate(@RequestBody UserRequestDTO dto){
        User user = userService.getByCredentials(dto.getUserId(), dto.getPassword(), passwordEncoder);
        UserResponseDTO response = new UserResponseDTO(user);
        final String token = tokenProvider.createToken(user);
        response.setToken(token);
        return response;
    }

    @GetMapping("/{id}")
    public UserResponseDTO retrieveUser(@PathVariable Long id){
        User user = userService.retrieveById(id);
        UserResponseDTO dto = new UserResponseDTO(user);
        return dto;
    }

    @PutMapping("/{id}")
    public void updateUser(@AuthenticationPrincipal String userId, @PathVariable Long id, @RequestBody UserRequestDTO dto){
        User user = UserResponseDTO.toEntity(dto);
        userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@AuthenticationPrincipal String userId, @PathVariable Long id){
        userService.delete(id);
    }

}
