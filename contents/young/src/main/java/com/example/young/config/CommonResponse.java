package com.example.young.config;

// enum은 열거형 상수(서로 연견관된 상수들의 집합)
public enum CommonResponse {

    SUCCESS(0, "Success"), FAIL(-1, "Fail");

    int code;
    String msg;

    CommonResponse(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
