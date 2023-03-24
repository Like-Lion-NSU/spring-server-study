package com.example.hana.Controller;

import com.example.hana.Dto.SignInResultDto;
import com.example.hana.Dto.SignUpResultDto;
import com.example.hana.Dto.UserSignInRequestDto;
import com.example.hana.Dto.UserSignUpRequestDto;
import com.example.hana.Service.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;
//import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Slf4j
@RestController
//@RequestMapping("/sign-api")
public class SignController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

  private final SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }

    //회원가입하는거
    @PostMapping("/sign-up")
    public SignUpResultDto signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto){
        logger.info("[signIn] 회원가입을 수행합니다. id : {}", userSignUpRequestDto.getId());

        //오류->userSignUpRequestDto로 시도
        SignUpResultDto signUpResultDto = signService.signUp(userSignUpRequestDto);
        if(signUpResultDto.getCode()==0)
            logger.info("[signUp] 회원가입을 완료했습니다. id : {}", userSignUpRequestDto.getId());
        return signUpResultDto;
    }

    //로그인
    @PostMapping("/sign-in")
    public SignInResultDto signIn(@RequestBody UserSignInRequestDto userSignInRequestDto) throws RuntimeException {

        SignInResultDto signInResultDto;
        logger.info("[signIn]로그인을 시도하고 있습니다. id : {}",
                userSignInRequestDto.getId());

        signInResultDto = signService.signIn(userSignInRequestDto);
        if(signInResultDto.getCode()==0){
            // signInResultDto ->long id
            logger.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", userSignInRequestDto.getId() , signInResultDto.getToken());
            return signInResultDto;
        }
        return signInResultDto;
    }

    @GetMapping("/exception")
    public void exceptionTest() throws RuntimeException{
        throw  new RuntimeException("접근이 금지되었습니다");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e){
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus= HttpStatus.BAD_REQUEST;

        logger.error("ExceptionHandler 호출, {}, {}",e.getCause(), e.getMessage());

        Map<String, String>map=new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
