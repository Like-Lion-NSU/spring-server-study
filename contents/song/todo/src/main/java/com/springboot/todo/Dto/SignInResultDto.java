package com.springboot.todo.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(callSuper=false)
public class SignInResultDto extends SignUpResultDto{
    private Long id;
    private String token;

}

