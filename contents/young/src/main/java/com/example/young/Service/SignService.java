package com.example.young.Service;

import com.example.young.dto.*;
import com.example.young.entity.User;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SignService {

    public Map<String, String> validateHandling(Errors errors);     // 회원가입 유효성 체크

    public void checkUserIdDuplication(UserSignUpRequestDto userSignUpRequestDto);  // 아이디 중복 체크

    public void checkNameDuplication(UserSignUpRequestDto userSignUpRequestDto);    // 이름 중복 체크

    public SignUpResultDto signUp(UserSignUpRequestDto userSignUpRequestDto);       // 회원가입

    //User 전체 조회
    public List<User> findUsers();

    //User 특정 조회
    public Optional<User> findOne(String id);

    //User 수정
    public void editUser(String id, UserEditRequestDto userEditRequestDto);

    //User 삭제
    public void deleteUser(Long id);

    //  SignInResultDto signUp(String id, String password, String name, String role);

    public SignInResultDto signIn(UserSignInRequestDto userSignInRequestDto) throws RuntimeException;   // 로그인


}
