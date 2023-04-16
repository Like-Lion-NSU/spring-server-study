package com.example.young.Controller;


import com.example.young.Service.SignService;
import com.example.young.Service.SignServiceImpl;
import com.example.young.dto.*;
import com.example.young.entity.User;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController     // @Controller에 @ResponseBody 추가된 어노테이션
@RequestMapping("/sign-api")
public class SignController {

    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;

    @Autowired
    public SignController(SignService signService){
        this.signService = signService;
    }

    // SignIn Controller
    @PostMapping(value = "/sign-in")
    public SignInResultDto signIn(@Valid @RequestBody UserSignInRequestDto userSignInRequestDto)    // 로그인 유효성 검사를 해야하는지..?
        throws RuntimeException{
        LOGGER.info("[sigiIn] 로그인을 시도하고 있습니다. id : {}, pw : ****",userSignInRequestDto.getId());
        SignInResultDto signInResultDto = signService.signIn(userSignInRequestDto);

        if(signInResultDto.getCode()==0){
            LOGGER.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", userSignInRequestDto.getId(), signInResultDto.getToken());
        }
        return signInResultDto;
    }

    // SignUp Controller
    @PostMapping(value = "/sign-up")
    public SignUpResultDto signUp(@Valid @RequestBody UserSignUpRequestDto userSignUpRequestDto, Errors errors, Model model){
            LOGGER.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}, role : {}", userSignUpRequestDto.getUserId(),
                    userSignUpRequestDto.getName(), userSignUpRequestDto.getRole());
            if(errors.hasErrors()){
                /* 회원가입 실패시 입력 데이터 값 유지 */
                model.addAttribute("userSignUpRequsetDto", userSignUpRequestDto);

                /* 유효성 통과 못한 필드와 메시지를 핸들링 */
                Map<String, String> validatorResult = signService.validateHandling(errors);
                for (String key : validatorResult.keySet()) {
                    model.addAttribute(key, validatorResult.get(key));
                }
                /* 회원가입 시 중복 체크 */
                signService.checkUserIdDuplication(userSignUpRequestDto);
                signService.checkNameDuplication(userSignUpRequestDto);

            }

            SignUpResultDto signUpResultDto = signService.signUp(userSignUpRequestDto);

            LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", userSignUpRequestDto.getUserId());
            return signUpResultDto;
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

    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException{
        throw new RuntimeException("접근이 금지되었습니다.");
    }

    // @ControllerAdvice  쓰고싶어요
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(Exception e){
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
 }
