package com.springboot.todo.Service;

import com.springboot.todo.Config.CommonResponse;
import com.springboot.todo.Config.JwtTokenProvider;
import com.springboot.todo.Dto.*;
import com.springboot.todo.Entity.User;
import com.springboot.todo.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    /*
    의존성 주입
    Bean을 찾아주는 역할을 함
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.jwtTokenProvider=jwtTokenProvider;
        this.passwordEncoder=passwordEncoder;
    }

    //User 등록
    @Override
    public SignUpResultDto signUp(UserSignUpRequestDto userSignUpRequestDto){
        SignUpResultDto signUpResultDto = new SignUpResultDto();
        validateDuplicateUser(userSignUpRequestDto); //회원 중복 확인
        log.info("[getSignUpResult] 회원 가입 정보 전달");
        User user;
        if(userSignUpRequestDto.getRole().equals("admin")) {

            user = User.builder()
                    .userId(userSignUpRequestDto.getId())
                    .name(userSignUpRequestDto.getName())
                    .password(passwordEncoder.encode(userSignUpRequestDto.getPassword()))
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
            log.info("[getSignUpResult] admin");
        }else{
            user = User.builder()
                    .userId(userSignUpRequestDto.getId())
                    .name(userSignUpRequestDto.getName())
                    .password(passwordEncoder.encode(userSignUpRequestDto.getPassword()))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
            log.info("[getSignUpResult] role_user");
        }
        User savedUser = userRepository.save(user);
        log.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과 값 주입");
        if(!savedUser.getName().isEmpty())
        {
            log.info("[getSignResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        }else {
            log.info("[getSingUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    //동명이인 불가
    private void validateDuplicateUser(UserSignUpRequestDto userSignUpRequestDto){ //회원 중복 확인
        userRepository.findByUserId(userSignUpRequestDto.getId()) //사용하고자 하는 아이디가 이미 존재하는지 repository에서 확인
                .ifPresent(m->{ //ifPresent : Boolean 타입, Optional 객체가 값을 가지고 있다면 true, 값이 없다면 false 리턴
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });
    }

    //로그인
    @Override
    public SignInResultDto signIn(UserSignInRequestDto userSignInRequestDto) throws RuntimeException{
        log.info("[getSignInResult] signDataHandler로 회원 정보 요청");
        User user = userRepository.findByUserId(userSignInRequestDto.getId()).get();
        log.info("[getSignInResult] Id : {}",userSignInRequestDto.getId());

        log.info("[getSignInResult] 패스워드 비교 수행");
        if(!passwordEncoder.matches(userSignInRequestDto.getPassword(),user.getPassword())){
            throw new RuntimeException();
        }
        log.info("[getSignResult] 패스워드 일치");

        log.info("[getSignInResult] SignInResultDto 객체 생성");

        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getUserId()), user.getRoles()))
                .build();
        log.info("[getSignInResult] getSignInResult 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    //User 전체 조회
    @Override
    public List<User> findUsers(){ //리턴타입 리스트
        return userRepository.findAll(); // 모든 회원 조회
    }

    //User 특정 조회
    @Override
    public Optional<User> findOne(String id){
        User user = userRepository.findByUserId(id).orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        return userRepository.findByUserId(id);
    }

    //User 수정
    @Override
    public void updateUser(String id, UserUpdateRequestDto userUpdateRequestDto){
        User user = userRepository.findByUserId(id).orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        user.setUserId(userUpdateRequestDto.getId());
        user.setName(userUpdateRequestDto.getName());
        user.setPassword(userUpdateRequestDto.getPassword());

        userRepository.save(user);
    }

    //User 삭제
    @Override
    public void deleteUser(Long id){
        userRepository.findById(id).orElseThrow(()-> new RuntimeException("존재하지 않는 회원입니다."));
        userRepository.deleteById(id);
    }

    private void setSuccessResult(SignUpResultDto result)
    {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto result)
    {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }
}
