package com.springboot.todo.Dto;

import com.springboot.todo.Entity.Todo;
import com.springboot.todo.Entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.springframework.data.jpa.domain.AbstractAuditable_.createdDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoSaveRequestDto {
//    private Long id;
    private String item;
    private Boolean isDone;

   public TodoSaveRequestDto(/*Long id,*/ String item, Boolean isDone){
       /*this.id=id;*/
       this.item=item;
       this.isDone=isDone;
   }
    public Todo toEntity() {
        return Todo.builder()
                /*.id(id)*/
                .item(item)
                .isDone(isDone)
                .build();
    }
}
