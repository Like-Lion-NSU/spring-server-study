package com.example.young.Service;

import com.example.young.dto.TodoEditRequestDto;
import com.example.young.dto.TodoSaveRequestDto;
import com.example.young.entity.Todo;
import com.example.young.entity.User;

import java.util.List;
import java.util.Optional;

public interface TodoService {

    public Long saveTodo(String id, TodoSaveRequestDto todoSaveRequestDto);

    public List<Todo> findTodos(Long id);

    public Optional<Todo> findById(Long id);

    public void editTodo(Long id, TodoEditRequestDto todoEditRequestDto);

    public void deleteTodo(Long id);
}
