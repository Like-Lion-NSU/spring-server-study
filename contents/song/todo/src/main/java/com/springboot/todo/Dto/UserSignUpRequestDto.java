package com.springboot.todo.Dto;

import com.springboot.todo.Entity.User;
import lombok.*;

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
//
//    public User toEntity(){
//        return User.builder()
//                .userId(id)
//                .name(name)
//                .password(password)
//                .roles(role)
//                .build();
//    }

}
