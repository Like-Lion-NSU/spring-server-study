package com.example.young.dto;

import com.example.young.entity.Todo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.springframework.data.jpa.domain.AbstractAuditable_.createdDate;


// 일정 저장
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoSaveRequestDto{

    @NotNull
    @Size(min = 1, max = 100)
    private String item;

    @NotNull
//    @PositiveOrZero
    private Boolean isDone;

    public TodoSaveRequestDto(String item, Boolean isDone){
        this.item=item;
        this.isDone=isDone;
    }

    public Todo toEntity(){
        return Todo.builder()
                .item(item)
                .isDone(isDone)
                .build();
    }
}
