package com.springboot.todo.Controller;


import com.springboot.todo.Dto.*;
import com.springboot.todo.Entity.User;
import com.springboot.todo.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@ControllerAdvice
@RestController
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    //회원가입
    @PostMapping("/sign-up")
    public SignUpResultDto signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto){ //json 형식
        try {
            log.info("[signIn] 회원가입을 시도하고 있습니다. id : {}, pw : ****", userSignUpRequestDto.getId());
            SignUpResultDto signUpResultDto = userService.signUp(userSignUpRequestDto);
            if (signUpResultDto.getCode() == 0)
                log.info("[signUp] 회원가입을 완료했습니다. id : {}", userSignUpRequestDto.getId());
            return signUpResultDto;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    //로그인
    @PostMapping("/sign-in")
    public SignInResultDto signIn(@RequestBody UserSignInRequestDto userSignInRequestDto) throws RuntimeException{
        log.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", userSignInRequestDto.getId());
        SignInResultDto signInResultDto = userService.signIn(userSignInRequestDto);

        if (signInResultDto.getCode() == 0) {
            log.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", userSignInRequestDto.getId(), signInResultDto.getToken());
            return signInResultDto;
        }
        return signInResultDto;
    }
    //User 전체 조회
    @GetMapping("/user")
    public List<User> user(){ //리스트 형식으로 리턴
        try {
            List<User> users = userService.findUsers(); //리스트 형식인 자료형 변수에 userService의 findUsers 메소드 리턴 값을 users에 저장
            return users;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    //User 조회
    @GetMapping("/user/{id}")
    public Optional<User> findOne(@PathVariable String id){ //optional 형식으로 리턴
        try {
            Optional<User> user = userService.findOne(id); //Optional 형식인 자료형 변수에 userService의 findOne 메소드 리턴 값을 user에 저장
            return user;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @PatchMapping("/user/{id}")
    public void updateUser(@PathVariable String id,@RequestBody UserUpdateRequestDto userUpdateRequestDto){ //url의 {}안의 url이름 변수명이 같아야함, 다르게 하고 싶을 경우 @Pathvariable(여기다가 명시)
        try {
            userService.updateUser(id, userUpdateRequestDto); //userService의 updateUser 메소드 파라미터에 id랑 userUpdateReqestDto 전달
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id){
        try {
            userService.deleteUser(id); //userService의 deleteUser 메소드에 시스템 아이디값 전달
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping(value="/exception")
    public void exceptionTest() throws RuntimeException{
        throw new RuntimeException("접근이 금지되었습니다.");
    }

    @ExceptionHandler(value=RuntimeException.class)
    public ResponseEntity<Map<String, String>> handle(RuntimeException e){
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        log.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
