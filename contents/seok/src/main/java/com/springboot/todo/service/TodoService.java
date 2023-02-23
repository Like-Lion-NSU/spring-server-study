package com.springboot.todo.service;

import com.springboot.todo.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    List<Todo> retrieveTodoList(Long id);
    Long saveTodo(Todo todo);
    Optional<Todo> retrieveTodo(Long id);
    void updateTodo(Todo todo);
    void deleteTodo(Long id);
}
