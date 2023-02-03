package com.springboot.todo.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String name;

    public UserUpdateRequestDto(String name){
        this.name=name;
    }
}
