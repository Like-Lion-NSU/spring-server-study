package com.repository;

import com.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepo {
    Todo save (Todo todo);

    Optional< Todo> findById(Long id);

    List<Todo> findAll();
}
