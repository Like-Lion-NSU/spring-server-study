package com.springboot.todo.repository.temp;

import com.springboot.todo.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepo {
    Todo save(Todo todo);
    Optional<Todo> findById(String userId);
    List<Todo> findAll();
}
