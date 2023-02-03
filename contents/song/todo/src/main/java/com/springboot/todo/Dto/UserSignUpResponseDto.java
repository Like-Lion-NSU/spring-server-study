package com.springboot.todo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSignUpResponseDto {

    private Boolean success;

    private int code;

    private String msg;
}
