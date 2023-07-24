package com.example.young.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// User 회원가입 요청 Dto
@Getter
@Builder
@NoArgsConstructor
public class UserSignUpRequestDto {
    private String id;
    private String password;
    private String name;
    private String role;

    public UserSignUpRequestDto(String id, String password, String name, String role){
        this.id=id;
        this.password=password;
        this.name=name;
        this.role=role;

    }
}
