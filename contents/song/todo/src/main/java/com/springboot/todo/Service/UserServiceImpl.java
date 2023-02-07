package com.springboot.todo.Service;

import com.springboot.todo.Dto.*;
import com.springboot.todo.Entity.User;
import com.springboot.todo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /*
    의존성 주입
    Bean을 찾아주는 역할을 함
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    //User 등록
    @Override
    public Long signUp(UserSignUpRequestDto userSignUpRequestDto){
        validateDuplicateUser(userSignUpRequestDto); //회원 중복 확인
        User user = userSignUpRequestDto.toEntity(); //엔티티로 변환
        userRepository.save(user); //repository에 저장
        return user.getId(); //시스템 아이디 리턴
    }

    //동명이인 불가
    private void validateDuplicateUser(UserSignUpRequestDto userSignUpRequestDto){ //회원 중복 확인
        userRepository.findByUserId(userSignUpRequestDto.getId()) //사용하고자 하는 아이디가 이미 존재하는지 repository에서 확인
                .ifPresent(m->{ //ifPresent : Boolean 타입, Optional 객체가 값을 가지고 있다면 true, 값이 없다면 false 리턴
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });
    }

    //User 전체 조회
    @Override
    public List<User> findUsers(){ //리턴타입 리스트
        return userRepository.findAll(); // 모든 회원 조회
    }

    //User 특정 조회
    @Override
    public Optional<User> findOne(String id){
        return userRepository.findByUserId(id);
    }

    //User 수정
    @Override
    public void updateUser(String id, UserUpdateRequestDto userUpdateRequestDto){
        User user = userRepository.findByUserId(id).orElse(null);
        user.setUserId(userUpdateRequestDto.getId());
        user.setName(userUpdateRequestDto.getName());
        user.setPassword(userUpdateRequestDto.getPassword());

        userRepository.save(user);
    }

    //User 삭제
    @Override
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
