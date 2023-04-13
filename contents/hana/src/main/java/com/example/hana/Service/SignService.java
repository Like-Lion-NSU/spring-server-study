package com.example.hana.Service;

import com.example.hana.Dto.SignInResultDto;
import com.example.hana.Dto.SignUpResultDto;
import com.example.hana.Dto.UserSignInRequestDto;
import com.example.hana.Dto.UserSignUpRequestDto;
import org.springframework.validation.Errors;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


public interface SignService {

   public Map<String, String> validateHandling(Errors errors);

   //중복 id 확인
  public void checkUserIdDuplication(UserSignUpRequestDto userSignUpRequestDto);

   //회원가입
   public SignUpResultDto signUp(UserSignUpRequestDto userSignUpRequestDto);
//로그인
   public SignInResultDto signIn(UserSignInRequestDto userSignInRequestDto) throws RuntimeException;

}
