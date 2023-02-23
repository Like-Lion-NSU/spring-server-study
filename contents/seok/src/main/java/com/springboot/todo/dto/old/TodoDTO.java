package com.springboot.todo.dto.old;

import com.springboot.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
    private Long id;
    private String item;
    private String userId;
    private boolean isDone;

    public TodoDTO(Todo todo){
        this.id = todo.getId();
        this.item = todo.getItem();
        this.userId = todo.getUser().getUserId();
        this.isDone = todo.isDone();
    }

    public static Todo toEntity(TodoDTO dto){
        return Todo.builder()
                .id(dto.getId())
                .item(dto.getItem())
                .isDone(dto.isDone())
                .build();
    }
}
