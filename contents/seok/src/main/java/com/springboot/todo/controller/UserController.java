package com.springboot.todo.controller;

import com.springboot.todo.dto.*;
import com.springboot.todo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> retrieveUsers(){
        List<UserResponseDto> users = userService.retrieveUsers();
        return users;
    }

    @PostMapping
    public SignUpResultDto signUp(@Validated @RequestBody SignUpRequestDto signUpRequestDto, Errors errors){
        logger.info("[signIn] 회원가입을 수행합니다. id : {}", signUpRequestDto.getId());
        if(errors.hasErrors()) {

        }
        SignUpResultDto signUpResultDto = userService.signUp(signUpRequestDto);
        if(signUpResultDto.getCode()==0){
            logger.info("[signUp] 회원가입을 완료했습니다. id : {}", signUpRequestDto.getId());
        }
        return signUpResultDto;
    }

    @PostMapping("/login")
    public SignInResultDto signIn(@RequestBody SignInRequestDto signInRequestDto) throws RuntimeException {
        logger.info("[signIn]로그인을 시도하고 있습니다. id : {}", signInRequestDto.getId());
        SignInResultDto signInResultDto = userService.signIn(signInRequestDto);
        if(signInResultDto.getCode()==0){
            logger.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", signInRequestDto.getId(), signInResultDto.getToken());
            return signInResultDto;
        }
        return signInResultDto;
    }

    @GetMapping("/{id}")
    public UserResponseDto retrieveUser(@PathVariable Long id){
        UserResponseDto user = userService.retrieveUser(id);
        return user;
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @Validated @RequestBody UserRequestDto userRequestDto, Errors errors){
        if(errors.hasErrors()) {

        }
        userService.updateUser(id, userRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @GetMapping("/exception")
    public void exception() throws RuntimeException{
        throw new RuntimeException("접근이 금지되었습니다");
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(Exception e){
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus= HttpStatus.BAD_REQUEST;

        logger.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
