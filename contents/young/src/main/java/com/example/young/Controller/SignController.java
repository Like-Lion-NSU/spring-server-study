package com.example.young.Controller;


import com.example.young.Service.SignService;
import com.example.young.Service.SignServiceImpl;
import com.example.young.dto.SignInResultDto;
import com.example.young.dto.SignUpResultDto;
import com.example.young.dto.UserSignInRequestDto;
import com.example.young.dto.UserSignUpRequestDto;
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
import java.util.Map;

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
            LOGGER.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}, role : {}", userSignUpRequestDto.getId(),
                    userSignUpRequestDto.getName(), userSignUpRequestDto.getRole());
            if(errors.hasErrors()){
                /* 회원가입 실패시 입력 데이터 값 유지 */
                model.addAttribute("userSignUpRequsetDto", userSignUpRequestDto);

                /* 유효성 통과 못한 필드와 메시지를 핸들링 */
                Map<String, String> validatorResult = signService.validateHandling(errors);
                for (String key : validatorResult.keySet()) {
                    model.addAttribute(key, validatorResult.get(key));
                }
                signService.checkUserIdDuplication(userSignUpRequestDto);
                signService.checkNameDuplication(userSignUpRequestDto);

            }

            SignUpResultDto signUpResultDto = signService.signUp(userSignUpRequestDto);

            LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", userSignUpRequestDto.getId());
            return signUpResultDto;
        }

    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException{
        throw new RuntimeException("접근이 금지되었습니다.");
    }

//    @ControllerAdvice  쓰고싶어요
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e){
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
