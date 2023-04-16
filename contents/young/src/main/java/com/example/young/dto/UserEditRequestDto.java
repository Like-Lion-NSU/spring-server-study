package com.example.young.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEditRequestDto {
    private String name;
    private String id;

    private String password;
    public UserEditRequestDto(String name, String id, String password){
        this.name=name;
        this.id=id;
        this.password=password;
    }
}
