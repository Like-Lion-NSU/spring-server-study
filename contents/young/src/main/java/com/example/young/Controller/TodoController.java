package com.example.young.Controller;


import com.example.young.dto.TodoSaveRequestDto;
import com.example.young.dto.TodoEditRequestDto;
import com.example.young.entity.Todo;
import com.example.young.entity.User;
import com.example.young.Service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class TodoController {
    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @PostMapping("/todo/{id}")
    public Long saveTodo(@PathVariable String id, @RequestBody TodoSaveRequestDto todoSaveRequestDto){
        Long saveTodo = todoService.saveTodo(id,todoSaveRequestDto);
        return saveTodo;
    }

    @GetMapping("/todos/{id}")
    public List<Todo> findTodos(@PathVariable Long id){
        List<Todo> todos = todoService.findTodos(id);
        return todos;
    }

    @GetMapping("/todo/{id}")
    public Optional<Todo> todo(@PathVariable Long id){
        Optional<Todo> todo = todoService.findById(id);
        return todo;
    }

    @PatchMapping("/todo/{id}")
    public void editTodo(@PathVariable Long id, @RequestBody TodoEditRequestDto todoEditRequestDto){
        todoService.editTodo(id, todoEditRequestDto);
    }

    @DeleteMapping("/todo/{id}")
    public void deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
    }


}
