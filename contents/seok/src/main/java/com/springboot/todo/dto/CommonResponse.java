package com.springboot.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponse {
    SUCCESS(0, "Success"), FAIL(-1, "Fail");
    int code;
    String msg;

}
