package com.springboot.todo.dto;

import com.springboot.todo.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SignUpRequestDto {
    @NotBlank
    private String id;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    private String password;

    @NotBlank
    @Size(min=2)
    private String name;

    // Enum은??
    @NotBlank

    private String role;

    public static User toEntity(SignUpRequestDto dto){
        User user = User.builder()
                .userId(dto.getId())
                .userName(dto.getName())
                .build();
        return user;
    }
}