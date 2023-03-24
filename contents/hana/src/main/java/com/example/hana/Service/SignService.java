package com.example.hana.Service;

import com.example.hana.Dto.SignInResultDto;
import com.example.hana.Dto.SignUpResultDto;
import com.example.hana.Dto.UserSignInRequestDto;
import com.example.hana.Dto.UserSignUpRequestDto;


public interface SignService {

   public SignUpResultDto signUp(UserSignUpRequestDto userSignUpRequestDto);

 //  SignInResultDto signUp(String id, String password, String name, String role);

   public SignInResultDto signIn(UserSignInRequestDto userSignInRequestDto) throws RuntimeException;
}
