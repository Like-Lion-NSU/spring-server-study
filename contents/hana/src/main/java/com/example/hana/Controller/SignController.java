package com.example.hana.Controller;

import org.springframework.ui.Model;
import com.example.hana.Dto.*;
import com.example.hana.Service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.hana.Entity.User;
import java.util.HashMap;
import java.util.Optional;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;

@RestController
//@RequestMapping("/sign-api") //url과 맵핑
public class SignController {

    Logger logger = LoggerFactory.getLogger(SignController.class);

   private final SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }


      @PostMapping("/sign-up")
    public SignUpResultDto signUp(@Valid @RequestBody UserSignUpRequestDto userSignUpRequestDto, Errors errors, Model model) {

        logger.info("[signUp] 회원가입을 수행합니다. id : {}", userSignUpRequestDto.getUserId());
        if (errors.hasErrors()) {
            // 회원가입 실패시, 입력 데이터를 유지
           model.addAttribute("userSignUpRequestDto", userSignUpRequestDto);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = signService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            } logger.info("유효성 통과 못함");
            signService.checkUserIdDuplication(userSignUpRequestDto);

        }

          SignUpResultDto signUpResultDto = signService.signUp(userSignUpRequestDto);
          logger.info("[signUp]회원가입을 완료했습니다. id: {}", userSignUpRequestDto.getUserId());
        return signUpResultDto;
    }

//    @PostMapping("/sign-up")
//    public SignUpResultDto signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto){
//        logger.info("[signIn] 회원가입을 수행합니다. id : {}", userSignUpRequestDto.getId());
//
//        SignUpResultDto signUpResultDto = signService.signUp(userSignUpRequestDto);
//
//         if(signUpResultDto.getCode()==0)
//            logger.info("[signUp] 회원가입을 완료했습니다. id : {}", userSignUpRequestDto.getId());
//        return signUpResultDto;
//    }

    //로그인
    @PostMapping("/sign-in")
    public SignInResultDto signIn(@RequestBody UserSignInRequestDto userSignInRequestDto) throws RuntimeException {

        SignInResultDto signInResultDto;
        logger.info("[signIn]로그인을 시도하고 있습니다. id : {}",
                userSignInRequestDto.getId());

        signInResultDto = signService.signIn(userSignInRequestDto);

        if(signInResultDto.getCode()==0){
            logger.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", userSignInRequestDto.getId(), signInResultDto.getToken());
            return signInResultDto;
        }
        return signInResultDto;
    }

    //User 전체 조회
    @GetMapping("/user")
    public List<User> user(){ //리스트 형식으로 리턴
        try {
            List<User> users = signService.findUsers(); //리스트 형식인 자료형 변수에 userService의 findUsers 메소드 리턴 값을 users에 저장
            return users;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    //User 조회
    @GetMapping("/user/{id}")
    public Optional<User> findOne(@PathVariable String id){ //optional 형식으로 리턴
//        try {
        Optional<User> user = signService.findOne(id); //Optional 형식인 자료형 변수에 userService의 findOne 메소드 리턴 값을 user에 저장
        return user;
//        }catch(Exception e){
//            e.printStackTrace();
//            throw e;
//        }
    }

    @PatchMapping("/user/{id}")
    public void editUser(@PathVariable String id,@RequestBody UserEditRequestDto userEditRequestDto){ //url의 {}안의 url이름 변수명이 같아야함, 다르게 하고 싶을 경우 @Pathvariable(여기다가 명시)
//        try {
        signService.editUser(id, userEditRequestDto); //userService의 updateUser 메소드 파라미터에 id랑 userUpdateReqestDto 전달
//        }catch(Exception e){
//            e.printStackTrace();
//            throw e;
//        }
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id){
//        try {
        signService.deleteUser(id); //userService의 deleteUser 메소드에 시스템 아이디값 전달
//        }catch(Exception e){
//            e.printStackTrace();
//            throw e;
//        }
    }

    @GetMapping(value="/exception")
    public void exceptionTest() throws RuntimeException{
        throw new RuntimeException("접근이 금지되었습니다.");
    }
    //예외
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(Exception e){
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus= HttpStatus.BAD_REQUEST;

        logger.error("ExceptionHandler 호출, {}, {}",e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

}
