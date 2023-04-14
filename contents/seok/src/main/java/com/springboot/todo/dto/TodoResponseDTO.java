package com.springboot.todo.dto;

import com.springboot.todo.entity.Todo;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TodoResponseDto {
    private Long id;
    private String item;
    private String userId;
    private boolean isDone;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public TodoResponseDto(final Todo todo){
        this.id = todo.getId();
        this.item = todo.getItem();
        this.userId = todo.getUser().getUserId();
        this.isDone = todo.isDone();
        this.createdDate = todo.getCreatedDate();
        this.updatedDate = todo.getUpdatedDate();
    }


}
