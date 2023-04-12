package com.example.young.Service;

import com.example.young.dto.SignInResultDto;
import com.example.young.dto.SignUpResultDto;
import com.example.young.dto.UserSignInRequestDto;
import com.example.young.dto.UserSignUpRequestDto;
import org.springframework.validation.Errors;

import java.util.Map;

public interface SignService {

    public Map<String, String> validateHandling(Errors errors);     // 회원가입 유효성 체크

    public void checkUserIdDuplication(UserSignUpRequestDto userSignUpRequestDto);  // 아이디 중복 체크

    public void checkNameDuplication(UserSignUpRequestDto userSignUpRequestDto);    // 이름 중복 체크

    public SignUpResultDto signUp(UserSignUpRequestDto userSignUpRequestDto);       // 회원가입

    //  SignInResultDto signUp(String id, String password, String name, String role);

    public SignInResultDto signIn(UserSignInRequestDto userSignInRequestDto) throws RuntimeException;   // 로그인


}
