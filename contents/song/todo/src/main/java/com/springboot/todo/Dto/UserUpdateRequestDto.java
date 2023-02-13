package com.springboot.todo.Dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequestDto {
    private String name;
    private String id;

    private String password;
    public UserUpdateRequestDto(String name, String id, String password){
        this.name=name;
        this.id=id;
        this.password=password;
    }
}
