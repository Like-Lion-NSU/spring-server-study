package com.example.hana.Dto;

import com.example.hana.Entity.Todo;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoSaveRequestDto {
    @NotNull
    private String item;
    @NotNull
    private Boolean isDone;

    public TodoSaveRequestDto(String item, Boolean isDone){
        this.item=item;
        this.isDone=isDone;
    }
    public Todo toEntity() {
        return Todo.builder().item(item).isDone(isDone).build();
    }
}