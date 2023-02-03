package com.springboot.todo.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoEditRequestDto {
    private Boolean isDone;

    public TodoEditRequestDto(Boolean isDone){
        this.isDone=isDone;
    }
}
