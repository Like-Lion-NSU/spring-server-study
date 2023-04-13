package com.springboot.todo.Dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
