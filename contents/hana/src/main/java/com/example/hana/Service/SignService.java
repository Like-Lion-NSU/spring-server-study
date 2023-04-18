package com.example.hana.Service;

import com.example.hana.Dto.*;
import com.example.hana.Entity.User;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface SignService {
//회원가입 유효성 체크
   public Map<String, String> validateHandling(Errors errors);

   //중복 id 확인
  public void checkUserIdDuplication(UserSignUpRequestDto userSignUpRequestDto);

   //회원가입
   public SignUpResultDto signUp(UserSignUpRequestDto userSignUpRequestDto);
   //로그인
   public SignInResultDto signIn(UserSignInRequestDto userSignInRequestDto) throws RuntimeException;

   //User 전체 조회
    public List<User> findUsers();

    //User 특정 조회
    public Optional<User> findOne(String id);

    //User 수정
    public void editUser(String id, UserEditRequestDto userUpdateRequestDto);

    //User 삭제
    public void deleteUser(Long id);
}
