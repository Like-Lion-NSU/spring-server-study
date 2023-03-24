package com.example.hana.Service;

import com.example.hana.Dto.TodoEditRequestDto;
import com.example.hana.Dto.TodoSaveRequestDto;
import com.example.hana.Entity.Todo;


import java.util.List;
import java.util.Optional;

public interface TodoService {

    public Long saveTodo(String id, TodoSaveRequestDto todoSaveRequestDto);


    public List<Todo> findTodos(Long id);


     public Optional<Todo> findById(Long id);


     public void editTodo(Long id, TodoEditRequestDto todoEditRequestDto);


     public void deleteTodo(Long id);
}
