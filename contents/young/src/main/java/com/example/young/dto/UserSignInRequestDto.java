package com.example.young.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

// User 로그인 요청 Dto
@Getter
@NoArgsConstructor
public class UserSignInRequestDto {

    @NotBlank
    @Size(min=6, max=12, message = "아이디는 2자 이상 12자 이하입니다.")
    String id;

    @NotBlank
//    @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 사용하세요.")
    String password;

    public UserSignInRequestDto(String id, String password){
        this.id=id;
        this.password=password;
    }
}
