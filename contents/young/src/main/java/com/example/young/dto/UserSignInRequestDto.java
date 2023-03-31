package com.example.young.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

// User 로그인 요청 Dto
@Getter
@NoArgsConstructor
public class UserSignInRequestDto {
    String id;
    String password;

    public UserSignInRequestDto(String id, String password){
        this.id=id;
        this.password=password;
    }
}
