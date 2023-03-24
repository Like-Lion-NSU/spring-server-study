package com.example.young.dto;

import com.example.young.entity.Todo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.springframework.data.jpa.domain.AbstractAuditable_.createdDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoSaveRequestDto{
    private String item;
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
