package com.example.hana.Dto;

import lombok.*;

//로그인 성공 후 토큰 값을 담을 dto
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(callSuper=false)
public class SignInResultDto extends SignUpResultDto{

   // private Long id;
    private String token;
}