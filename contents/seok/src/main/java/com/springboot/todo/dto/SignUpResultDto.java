package com.springboot.todo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpResultDto {
    private boolean success;
    private int code;
    private String msg;
}
