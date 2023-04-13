package com.example.hana.Dto;

;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import com.example.hana.Entity.User;

@Getter
@Builder
@NoArgsConstructor
public class UserSignUpRequestDto {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 20)
    private String password;

    private String name;

    private String role;

    public UserSignUpRequestDto(String id, String password, String name, String role){
        this.id=id;
        this.password=password;
        this.name=name;
        this.role=role;

    }
    public UserSignUpRequestDto toEntity() {
        return UserSignUpRequestDto.builder().id(id).password(password).build();
    }
}
