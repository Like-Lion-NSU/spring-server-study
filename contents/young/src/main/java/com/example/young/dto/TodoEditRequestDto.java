package com.example.young.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 일정 완료 여부
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoEditRequestDto {

    @NotNull
//    @PositiveOrZero
    private Boolean isDone;

    public TodoEditRequestDto(Boolean isDone){
        this.isDone = isDone;
    }

}
