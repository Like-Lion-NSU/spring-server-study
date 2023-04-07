package com.springboot.todo.dto;

import com.springboot.todo.dto.old.TodoDTO;
import com.springboot.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDTO {
    private String item;
    private boolean isDone;

    public TodoResponseDTO(Todo todo){
        this.item = todo.getItem();
        this.isDone = todo.isDone();
    }

    public static Todo toEntity(TodoRequestDTO dto){
        return Todo.builder()
                .item(dto.getItem())
                .isDone(dto.isDone())
                .build();
    }
}
