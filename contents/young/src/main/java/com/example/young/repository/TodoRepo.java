package com.example.young.repository;

import com.example.young.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepo {
    Todo save(Todo todo);
    Optional<Todo> findbyId(Long id);
    List<Todo> findAll();
}
