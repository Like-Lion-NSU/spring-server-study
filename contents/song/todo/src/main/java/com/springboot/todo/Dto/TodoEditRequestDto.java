package com.springboot.todo.Dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoEditRequestDto {
    private Boolean isDone;

    public TodoEditRequestDto(Boolean isDone){
        this.isDone=isDone;
    }
}
