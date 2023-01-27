package com.springboot.todo.Repository;

import com.springboot.todo.entity.Todo;
import com.springboot.todo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemsRepository {
    Todo save(Todo todo);
    Optional<Todo> findById(Long id);
    List<Todo> findAll();
}
