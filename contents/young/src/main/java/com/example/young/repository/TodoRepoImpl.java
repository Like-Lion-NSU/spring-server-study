package com.example.young.repository;

import com.example.young.entity.Todo;
import java.util.*;

public class TodoRepoImpl implements TodoRepo {
    private static Map<Long, Todo> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Todo save(Todo todo) {
        todo.setId(++sequence);
        store.put(todo.getId(), todo);
        return todo;
    }

    @Override
    public Optional<Todo> findbyId(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Todo> findAll() {
        return null;
    }

    public void clearStore(){
        store.clear();
    }
}
