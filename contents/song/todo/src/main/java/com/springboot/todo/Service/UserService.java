package com.springboot.todo.Service;

import com.springboot.todo.Dto.*;
import com.springboot.todo.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    //User 등록
    public SignUpResultDto signUp(UserSignUpRequestDto userSignUpRequestDto);

    //로그인
    public SignInResultDto signIn(UserSignInRequestDto userSignInRequestDto) throws RuntimeException;

    //User 전체 조회
    public List<User> findUsers();

    //User 특정 조회
    public Optional<User> findOne(String id);

    //User 수정
    public void updateUser(String id, UserUpdateRequestDto userUpdateRequestDto);

    //User 삭제
    public void deleteUser(Long id);

}
