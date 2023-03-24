package com.example.hana.Dto;

import lombok.*;
import com.example.hana.Entity.User;

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
