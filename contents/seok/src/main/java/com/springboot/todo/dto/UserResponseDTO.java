package com.springboot.todo.dto;

import com.springboot.todo.dto.old.UserDTO;
import com.springboot.todo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String userId;
    private String password;
    private String userName;
    private String token;

    public UserResponseDTO(User user){
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.userName = user.getUserName();
    }

    public static User toEntity(UserRequestDTO dto) {
        return User.builder()
                .userId(dto.getUserId())
                .password(dto.getPassword())
                .userName(dto.getUserName())
                .build();
    }
}
