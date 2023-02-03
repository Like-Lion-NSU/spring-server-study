package com.springboot.todo.Dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignInRequestDto {
    private String id;

    private String password;
}
