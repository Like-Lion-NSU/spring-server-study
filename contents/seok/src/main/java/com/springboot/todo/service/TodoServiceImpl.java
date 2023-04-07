package com.springboot.todo.service;

import com.springboot.todo.entity.Todo;
import com.springboot.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService{

    private TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> retrieveTodoList(Long id) {
        return todoRepository.findByUser_Id(id);
    }

    @Override
    public Long saveTodo(Todo todo) {
        todoRepository.save(todo);
        return todo.getId();
    }

    @Override
    public Optional<Todo> retrieveTodo(Long id) {
        return todoRepository.findById(id);
    }

    @Override
    public void updateTodo(Todo todo) {
        todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
