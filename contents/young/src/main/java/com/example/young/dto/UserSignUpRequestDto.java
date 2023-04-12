package com.example.young.dto;

import com.example.young.entity.Todo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// User 회원가입 요청 Dto
@Getter
@Builder
@NoArgsConstructor
public class UserSignUpRequestDto {
    @NotBlank
    @Size(min=6, max=12, message = "아이디는 2자 이상 12자 이하입니다.")
    private String id;

    @NotBlank
//    @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 사용하세요.")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,4}$", message = "이름은 특수문자를 제외한 2~4자리여야 합니다.")
    private String name;


    private String role;


    public UserSignUpRequestDto(String id, String password, String name, String role){
        this.id=id;
        this.password=password;
        this.name=name;
        this.role=role;
    }
    public UserSignUpRequestDto toEntity(){
        return UserSignUpRequestDto.builder()
                .id(id)
                .password(password)
                .name(name)
                .role(role)
                .build();
    }
}
