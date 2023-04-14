package com.springboot.todo.service;

import com.springboot.todo.dto.TodoRequestDto;
import com.springboot.todo.dto.TodoResponseDto;

import java.util.List;

public interface TodoService {
    List<TodoResponseDto> retrieveTodos(String userId);

    TodoResponseDto retrieveTodo(Long id) throws Exception;

    Long saveTodo(String userId, TodoRequestDto dto);

    void updateTodo(Long id, TodoRequestDto dto);

    void deleteTodo(Long id) throws Exception;
}
