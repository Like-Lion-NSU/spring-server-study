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
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
  //  @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=.\\S+$).{8,16}")
            //비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.
    @Size(min = 8, max = 20)
    private String password;

    private String name;

    private String role;

    public UserSignUpRequestDto(String userId, String password, String name, String role){
        this.userId=userId;
        this.password=password;
        this.name=name;
        this.role=role;

    }
    public UserSignUpRequestDto toEntity() {
        return UserSignUpRequestDto.builder().userId(userId).password(password).build();
    }
}
