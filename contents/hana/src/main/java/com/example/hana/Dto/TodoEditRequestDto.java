package com.example.hana.Dto;

import lombok.*;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class TodoEditRequestDto {

    private Boolean isDone;

    public TodoEditRequestDto(Boolean isDone){
        this.isDone=isDone;
    }
}
