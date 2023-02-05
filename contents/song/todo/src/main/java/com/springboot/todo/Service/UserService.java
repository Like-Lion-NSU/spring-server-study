package com.springboot.todo.Service;

import com.springboot.todo.Dto.UserSignInRequestDto;
import com.springboot.todo.Dto.UserSignInResponseDto;
import com.springboot.todo.Dto.UserSignUpResponseDto;
import com.springboot.todo.Dto.UserSignUpRequestDto;

public interface UserService {
    //User 등록
    public Long signUp(UserSignUpRequestDto userSignUpRequestDto);

    //로그인
    public Long signUp(UserSignInRequestDto userSignInRequestDto) throws RuntimeException;
    //User 조회

    //User 수정

    //User 삭제

}
