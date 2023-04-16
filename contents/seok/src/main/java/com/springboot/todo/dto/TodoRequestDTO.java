package com.springboot.todo.dto;

import com.springboot.todo.entity.Todo;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TodoRequestDto {
    @NotBlank
    private String item;

    @AssertTrue
    private boolean isDone;

    public static Todo toEntity(final TodoRequestDto dto) {
        return Todo.builder()
                .item(dto.getItem())
                .isDone(dto.isDone())
                .build();
    }

}