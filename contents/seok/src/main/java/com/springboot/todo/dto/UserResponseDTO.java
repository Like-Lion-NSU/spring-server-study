package com.springboot.todo.dto;

import com.springboot.todo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDto {
    private String id;
    private String name;
    private String role;

    public UserResponseDto(User user){
        this.id = user.getUserId();
        this.name = user.getUserName();
        this.role = user.getRoles().get(0);
    }
}
