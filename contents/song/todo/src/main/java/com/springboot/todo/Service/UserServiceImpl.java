package com.springboot.todo.Service;

import com.springboot.todo.Dto.UserSignInRequestDto;
import com.springboot.todo.Dto.UserSignInResponseDto;
import com.springboot.todo.Dto.UserSignUpResponseDto;
import com.springboot.todo.Dto.UserSignUpRequestDto;
import com.springboot.todo.Entity.User;
import com.springboot.todo.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    //User 등록
    @Override
    public UserSignUpResponseDto signUp(UserSignUpRequestDto userSignUpRequestDto){
        UserSignUpResponseDto userSignUpResponseDto = new UserSignUpResponseDto();
        User user = userSignUpRequestDto.toEntity();
        userRepository.save(user);
        return userSignUpResponseDto;
    }

    @Override
    public UserSignInResponseDto signUp(UserSignInRequestDto userSignInRequestDto) throws RuntimeException {
        return null;
    }
    //로그인


    //User 조회

    //User 수정

    //User 삭제
}
