package com.example.young.Service;

import com.example.young.config.CommonResponse;
import com.example.young.config.JwtTokenProvider;
import com.example.young.dto.SignInResultDto;
import com.example.young.dto.SignUpResultDto;
import com.example.young.dto.UserSignInRequestDto;
import com.example.young.dto.UserSignUpRequestDto;
import com.example.young.entity.User;
import com.example.young.repository.UserRepo;
import com.example.young.repository.UserRepoJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class SignServiceImpl implements SignService{

    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);
    // 회원가입과 로그인 구현을 위해 3개 객체 의존성 주입
    public UserRepoJPA userRepoJPA;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;
    //생성자
    @Autowired
    public SignServiceImpl(UserRepoJPA userRepoJPA, JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder){
        this.userRepoJPA = userRepoJPA;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
            /*  key : valid_{dtd 필드명}, Message : dto에서 작성한 message 값
                erros.getFieldErrors() : 유효성 검사 실패 필드 목록 가져오기
                error.getField() : 유효성 검사 실패 필드명 가져오기
                error.getDefaultMessage() : 유효성 검사 실패한 필드에 정의된 메시지 가져오기 */
        }

        return validatorResult;
    }

    @Override       // 회원가입
    public SignUpResultDto signUp(UserSignUpRequestDto userSignUpRequestDto) {
        LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");
        User user;

        if(userSignUpRequestDto.getRole().equalsIgnoreCase("admin")){       //ADMIN
            user = User.builder()
                    .userId(userSignUpRequestDto.getId())
                    .name(userSignUpRequestDto.getName())
                    .password(passwordEncoder.encode(userSignUpRequestDto.getPassword()))   // Password 암호화
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        } else {    // USER
            user = User.builder()
                    .userId(userSignUpRequestDto.getId())
                    .name(userSignUpRequestDto.getName())
                    .password(passwordEncoder.encode(userSignUpRequestDto.getPassword()))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        User savedUser = userRepoJPA.save(user);
        SignUpResultDto signUpResultDto = new SignInResultDto();

        LOGGER.info("[getSingUpResult] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if(!savedUser.getName().isEmpty()){     // 저장 되었는지 확인 로직
            LOGGER.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            LOGGER.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override   // 로그인
    public SignInResultDto signIn(UserSignInRequestDto userSignInRequestDto) throws RuntimeException {
        LOGGER.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        User user = userRepoJPA.findByUserId(userSignInRequestDto.getId()).get();
        // ID를 기반으로 UserRepoJPA에서 user entity get
        LOGGER.info("[getSingInResult] Id : {}", userSignInRequestDto.getId());

        LOGGER.info("[getSingInResult] 패스워드 비교 수행");
        if(!passwordEncoder.matches(userSignInRequestDto.getPassword(), user.getPassword())){   // password 비교
            throw new RuntimeException();
        }
        LOGGER.info("[getSignInResult] 패스워드 일치");

        LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getUserId()), user.getRoles()))
                .build();
        // jwtTokenProvider를 통해 Id와 role 값을 전달하여 토큰 생성 후 Response에 담아 전달
        LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    @Override
    @Transactional(readOnly = true)     // 트랜젝션 중 read만 실시. 즉 cud는 멈춰있음
    public void checkUserIdDuplication(UserSignUpRequestDto userSignUpRequestDto){        // 아이디 중복 확인
        boolean usernameDuplicate = userRepoJPA.existsByUserId(userSignUpRequestDto.toEntity().getId());
        if (usernameDuplicate) {
            throw new IllegalStateException("이미 존재하는 아이디입니다. 다른 아이디를 입력해주세요.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void checkNameDuplication(UserSignUpRequestDto userSignUpRequestDto) {       // 이름 중복 확인
        boolean nicknameDuplicate = userRepoJPA.existsByName(userSignUpRequestDto.toEntity().getName());
        if (nicknameDuplicate) {
            throw new IllegalStateException("이미 존재하는 이름입니다. 다른 이름을 입력해주세요.");
        }
    }



    private void setSuccessResult(SignUpResultDto result){      // 성공 결과 데이터 설정
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto result){     // 실패 결과 데이터 설정
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

}

