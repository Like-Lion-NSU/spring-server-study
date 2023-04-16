package com.springboot.todo.service;

import com.springboot.todo.dto.*;

import java.util.List;

public interface UserService {
    SignUpResultDto signUp(SignUpRequestDto dto);
    SignInResultDto signIn(SignInRequestDto dto);
    List<UserResponseDto> retrieveUsers();
    UserResponseDto retrieveUser(Long id);
    void updateUser(Long id, UserRequestDto dto);
    void deleteUser(Long id);
}
