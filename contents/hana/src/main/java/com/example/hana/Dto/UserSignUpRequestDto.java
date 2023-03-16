package com.example.hana.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.hana.Entity.User;

@Getter
@NoArgsConstructor
public class UserSignUpRequestDto {
    private String id;
    private String password;
    private String name;
    public UserSignUpRequestDto(String id, String name, String password){
        this.id=id;this.name=name;
        this.password=password;
    }

    public User toEntity(){
        return User.builder()
                .userId(id)
                .name(name)
                .password(password)
                .build();
    }
}
