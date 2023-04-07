package com.example.hana.Service;

import com.example.hana.Dto.SignInResultDto;
import com.example.hana.Dto.SignUpResultDto;
import com.example.hana.Dto.UserSignInRequestDto;
import com.example.hana.Dto.UserSignUpRequestDto;


public interface SignService {
//회원가입
   public SignUpResultDto signUp(UserSignUpRequestDto userSignUpRequestDto);
//로그인
   public SignInResultDto signIn(UserSignInRequestDto userSignInRequestDto) throws RuntimeException;

}
