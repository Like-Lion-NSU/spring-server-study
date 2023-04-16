package com.springboot.todo.service;

import com.springboot.todo.dto.*;
import com.springboot.todo.entity.User;
import com.springboot.todo.repository.UserRepository;
import com.springboot.todo.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignUpResultDto signUp(SignUpRequestDto dto) throws IllegalStateException {
        logger.info("[getSignUpResult] 회원 가입 정보 전달");
        if(userRepository.existsByUserId(dto.getId())){
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
        User user = SignUpRequestDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        if(dto.getRole().equals("admin")) {
            user.setRoles(Collections.singletonList("ROLE_ADMIN"));
            logger.info("[getSignUpResult] admin");
        }else{
            user.setRoles(Collections.singletonList("ROLE_USER"));
            logger.info("[getSignUpResult] role_user");
        }
        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignUpResultDto();
        logger.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과 값 주입");
        if (!savedUser.getUserName().isEmpty()) {
            setSuccessResult(signUpResultDto);
            logger.info("[getSignResult] 정상 처리 완료");
        } else {
            setFailResult(signUpResultDto);
            logger.info("[getSingUpResult] 실패 처리 완료");
        }
        return signUpResultDto;
    }

    @Override
    public SignInResultDto signIn(SignInRequestDto dto) {
        logger.info("[getSignInResult] signDataHandler로 회원 정보 요청");
        User user = userRepository.findByUserId(dto.getId());
        logger.info("[getSignInResult] Id : {}",dto.getId());

        logger.info("[getSignInResult] 패스워드 비교 수행");
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        logger.info("[getSignResult] 패스워드 일치");

        logger.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signUpResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(user.getUserId(), user.getRoles()))
                .build();
        setSuccessResult(signUpResultDto);
        logger.info("[getSignInResult] getSignInResult 객체에 값 주입");
        return signUpResultDto;
    }

    @Override
    public List<UserResponseDto> retrieveUsers() {
        List<User> userList = userRepository.findAll();
        List<UserResponseDto> dtoList = userList.stream().map(UserResponseDto::new).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public UserResponseDto retrieveUser(Long id) throws NoSuchElementException{
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));
        UserResponseDto dto = new UserResponseDto(user);
        return dto;
    }

    @Override
    public void updateUser(Long id, UserRequestDto dto) throws NoSuchElementException{
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));
        user.setUserName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) throws NoSuchElementException{
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));
        userRepository.deleteById(user.getId());
    }

    private void setSuccessResult(SignUpResultDto dto){
        dto.setSuccess(true);
        dto.setCode(CommonResponse.SUCCESS.getCode());
        dto.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto dto){
        dto.setSuccess(false);
        dto.setCode(CommonResponse.SUCCESS.getCode());
        dto.setMsg(CommonResponse.SUCCESS.getMsg());
    }
}
