package com.example.hana.Service;

import com.example.hana.Config.CommonResponse;
import com.example.hana.Config.JwtTokenProvider;
import com.example.hana.Dto.SignInResultDto;
import com.example.hana.Dto.SignUpResultDto;
import com.example.hana.Dto.UserSignInRequestDto;
import com.example.hana.Dto.UserSignUpRequestDto;
import com.example.hana.Entity.User;
import com.example.hana.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@Service
@Slf4j
public class SignServiceImpl implements SignService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignUpResultDto signUp(UserSignUpRequestDto userSignUpRequestDto) {
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        LOGGER.info("[getSignUpResult]회원 가입 정보 전달");
        User user;

        if (userSignUpRequestDto.getRole().equals("admin")) {
            user = User.builder()
                    .userId(userSignUpRequestDto.getId())
                    .name(userSignUpRequestDto.getName())
                    .password(passwordEncoder.encode(userSignUpRequestDto.getPassword()))
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        } else {
            user = User.builder()
                    .userId(userSignUpRequestDto.getId())
                    .name(userSignUpRequestDto.getName())
                    .password(passwordEncoder.encode(userSignUpRequestDto.getPassword()))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }
        User savedUser = userRepository.save(user);
        // SignInResultDto signUpResultDto = new SignInResultDto();

        LOGGER.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if (!savedUser.getName().isEmpty()) {
            LOGGER.info("[getSignResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            LOGGER.info("[getSingUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public SignInResultDto signIn(UserSignInRequestDto userSignInRequestDto) throws RuntimeException {
        LOGGER.info("[getSignInResult] signDataHandler로 회원 정보 요청 ");

        User user = userRepository.findByUserId(userSignInRequestDto.getId()).get();
        logger.info("[getSignInResult] Id : {}",
                userSignInRequestDto.getId());

        LOGGER.info("[getSignInResult] 패스워드 비교 수행");
        if(!passwordEncoder.matches(userSignInRequestDto.getPassword(),user.getPassword())){
            throw new RuntimeException();
        }

        LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto =  SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getUserId()),
                user.getRoles()))
                .build();

        LOGGER.info("[getSignInResult] getSignInResult 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    private void setSuccessResult(SignUpResultDto result) {

        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());

    }

}
