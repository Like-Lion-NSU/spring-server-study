package com.springboot.todo.repository.old;

import com.springboot.todo.entity.Todo;

import java.util.*;

public class TodoRepoImpl implements TodoRepo{
    private static Map<Long, Todo> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Todo save(Todo todo) {
        todo.setId(++sequence);
        store.put(todo.getId(), todo);
        return todo;
    }

    @Override
    public Optional<Todo> findById(String userId) {
        return Optional.ofNullable(store.get(userId));
    }

    @Override
    public List<Todo> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
