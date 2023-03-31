package com.example.hana.Dto;

import lombok.*;

//투두 완료 여부 수정 dto
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class TodoEditRequestDto {

    private Boolean isDone;

    public TodoEditRequestDto(Boolean isDone){
        this.isDone=isDone;
    }
}
