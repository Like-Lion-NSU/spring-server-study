package com.springboot.todo.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSignInResponseDto extends UserSignUpResponseDto {
    private String token;
    @Builder
    public UserSignInResponseDto(Boolean success, int code, String msg, String token){
        super(success, code,msg);
        this.token=token;

    }


}
