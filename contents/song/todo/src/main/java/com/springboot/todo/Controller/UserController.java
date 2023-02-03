package com.springboot.todo.Controller;


import com.springboot.todo.Dto.UserSignUpResponseDto;
import com.springboot.todo.Dto.UserSignUpRequestDto;
import com.springboot.todo.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    //회원가입
    @PostMapping("/sign-up")
    public UserSignUpResponseDto signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto){
        log.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : { }, role : { }",userSignUpRequestDto.getId(),userSignUpRequestDto.getPassword(),userSignUpRequestDto.getRole());
        UserSignUpResponseDto userSignUpResponseDto = userService.signUp(userSignUpRequestDto);

        if(userSignUpResponseDto.getCode()==0)
            log.info("[signUp] 회원가입을 완료했습니다. id : {}", userSignUpRequestDto.getId());
        return userSignUpResponseDto;
    }

    //로그인
//    @PostMapping("sign-in")
//    public UserSignInResponseDto signIn(@RequestBody UserSignInRequestDto userSignInReqeustDto){
//
//    }
}
