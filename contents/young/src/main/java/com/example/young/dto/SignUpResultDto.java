package com.example.young.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


 // Response로 전달되는 Dto
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpResultDto {

     @NotNull
     @PositiveOrZero
    private boolean success;

    private int code;

    private String msg;
}
