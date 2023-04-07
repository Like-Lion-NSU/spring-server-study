package com.springboot.todo.Dto;

import com.springboot.todo.Entity.Todo;
import com.springboot.todo.Entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

import static org.springframework.data.jpa.domain.AbstractAuditable_.createdDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoSaveRequestDto {
    private String item;
    private Boolean isDone;

   public TodoSaveRequestDto(String item, Boolean isDone){
       this.item=item;
       this.isDone=isDone;
   }
    public Todo toEntity() {
        return Todo.builder()
                .item(item)
                .isDone(isDone)
                .build();
    }
}
