package com.springboot.todo.Repository;

import com.springboot.todo.Entity.Todo;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryTodoRepository implements ItemsRepository{

    private static Map<Long, Todo> store = new HashMap<>();
    private static long sequence = 0L;
    @Override
    public Todo save(Todo todo) {
        todo.setId(++sequence);
        store.put(todo.getId(), todo);
        return todo;
    }

    @Override
    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Todo> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
