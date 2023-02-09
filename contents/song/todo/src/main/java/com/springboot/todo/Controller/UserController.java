package com.springboot.todo.Controller;


import com.springboot.todo.Dto.UserSignUpRequestDto;
import com.springboot.todo.Dto.UserUpdateRequestDto;
import com.springboot.todo.Entity.User;
import com.springboot.todo.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    //회원가입
    @PostMapping("sign-up")
    public Long signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto){ //json 형식
        Long signUp = userService.signUp(userSignUpRequestDto); //userService의 signup 메소드
        return signUp;
    }

    //User 전체 조회
    @GetMapping("/user")
    public List<User> user(){ //리스트 형식으로 리턴
        List<User> users = userService.findUsers(); //리스트 형식인 자료형 변수에 userService의 findUsers 메소드 리턴 값을 users에 저장
        return users;
    }

    //User 조회
    @GetMapping("/user/{id}")
    public Optional<User> findOne(@PathVariable String id){ //optional 형식으로 리턴
        Optional<User> user = userService.findOne(id); //Optional 형식인 자료형 변수에 userService의 findOne 메소드 리턴 값을 user에 저장
        return user;
    }

    @PatchMapping("/user/{id}")
    public void updateUser(@PathVariable String id,@RequestBody UserUpdateRequestDto userUpdateRequestDto){ //url의 {}안의 url이름 변수명이 같아야함, 다르게 하고 싶을 경우 @Pathvariable(여기다가 명시)
        userService.updateUser(id, userUpdateRequestDto); //userService의 updateUser 메소드 파라미터에 id랑 userUpdateReqestDto 전달
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id); //userService의 deleteUser 메소드에 시스템 아이디값 전달
    }
}
