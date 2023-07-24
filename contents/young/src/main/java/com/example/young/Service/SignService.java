package com.example.young.Service;

import com.example.young.dto.SignInResultDto;
import com.example.young.dto.SignUpResultDto;
import com.example.young.dto.UserSignInRequestDto;
import com.example.young.dto.UserSignUpRequestDto;

public interface SignService {
    public SignUpResultDto signUp(UserSignUpRequestDto userSignUpRequestDto);       // 회원가입

    //  SignInResultDto signUp(String id, String password, String name, String role);

    public SignInResultDto signIn(UserSignInRequestDto userSignInRequestDto) throws RuntimeException;   // 로그인


}
