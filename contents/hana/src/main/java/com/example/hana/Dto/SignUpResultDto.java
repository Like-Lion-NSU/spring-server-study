package com.example.hana.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//성공 여부를 알려주고, http 상태코드 메시지를 저장하는 dto
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