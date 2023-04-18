package com.example.hana.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

//투두 완료 여부 수정 dto
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class TodoEditRequestDto {
    @NotNull
    private Boolean isDone;

    public TodoEditRequestDto(Boolean isDone){
        this.isDone=isDone;
    }
}
