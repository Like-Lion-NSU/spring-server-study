package com.springboot.todo.dto.old;

import com.springboot.todo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String userName;
    private String userId;
    private String password;
    private String token;

    public UserDTO(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.userId = user.getUserId();
        this.password = user.getPassword();
    }

    public static User toEntity(UserDTO dto){
        return User.builder()
                .id(dto.getId())
                .userName(dto.getUserName())
                .userId(dto.getUserId())
                .password(dto.getPassword())
                .build();
    }
}
