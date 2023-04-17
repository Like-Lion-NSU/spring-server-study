package com.example.hana.Service;

import com.example.hana.Config.CommonResponse;
import com.example.hana.Config.JwtTokenProvider;
import com.example.hana.Dto.*;
import com.example.hana.Entity.User;
import com.example.hana.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import java.util.List;
import java.util.Optional;

import java.util.*;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@Service
@Slf4j
public class SignServiceImpl implements SignService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입시 유효성 체크 진행
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors){
        Map<String, String> validatorResult = new HashMap<>();

     /* 유효성 검사에 실패한 필드 목록을 받음 */
   for (FieldError error : errors.getFieldErrors()) {
    String validKeyName = String.format("valid_%s", error.getField());
    validatorResult.put(validKeyName, error.getDefaultMessage());
       }
      return validatorResult;
    }

/* 아이디 중복 여부 확인 */
        @Transactional(readOnly = true)
     public void checkUserIdDuplication(UserSignUpRequestDto userSignUpRequestDto) {
     boolean userIdDuplicate = userRepository.existsByUserId(userSignUpRequestDto.toEntity().getUserId());
     if (userIdDuplicate) {
             throw new IllegalStateException("이미 존재하는 아이디입니다.");
    }
}
    //회원가입
    @Override
    public SignUpResultDto signUp(UserSignUpRequestDto userSignUpRequestDto) {
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        LOGGER.info("[getSignUpResult]회원 가입 정보 전달");
        User user;
      //role 객체가 admin일 땐 user entity의 roles 변수에 ROLE_ADMIN 저장
        if (userSignUpRequestDto.getRole().equals("admin")) {
            user = User.builder()
                    .userId(userSignUpRequestDto.getUserId())
                    .name(userSignUpRequestDto.getName())
                    .password(passwordEncoder.encode(userSignUpRequestDto.getPassword()))
                    .roles(Collections.singletonList("ROLE_ADMIN")) //user entity의 roles는 list여서 Collections.singletonList() 사용
                    .build();
        } else {
            user = User.builder()
                    .userId(userSignUpRequestDto.getUserId())
                    .name(userSignUpRequestDto.getName())
                    .password(passwordEncoder.encode(userSignUpRequestDto.getPassword()))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }
        User savedUser = userRepository.save(user);

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
   //로그인
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
    //User 전체 조회
    @Override
    public List<User> findUsers(){
        return userRepository.findAll();
    }

    //User 특정 조회
    @Override
    public Optional<User> findOne(String id) throws NoSuchElementException{
        User user = userRepository.findByUserId(id).orElseThrow(() -> new  NoSuchElementException("존재하지 않는 회원입니다."));
        return userRepository.findByUserId(id);
    }

      //User 수정
    @Override
    public void editUser(String id, UserEditRequestDto userUpdateRequestDto) throws NoSuchElementException{
        User user = userRepository.findByUserId(id).orElseThrow(() -> new  NoSuchElementException("존재하지 않는 회원입니다."));
        user.setUserId(userUpdateRequestDto.getId());
        user.setName(userUpdateRequestDto.getName());
        user.setPassword(userUpdateRequestDto.getPassword());

        userRepository.save(user);
    }
    //User 삭제
    @Override
    public void deleteUser(Long id) throws NoSuchElementException{
        userRepository.findById(id).orElseThrow(()-> new  NoSuchElementException("존재하지 않는 회원입니다."));
        userRepository.deleteById(id);
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
