package com.springboot.todo.Controller;

import com.springboot.todo.Dto.TodoEditRequestDto;
import com.springboot.todo.Dto.TodoSaveRequestDto;
import com.springboot.todo.Entity.Todo;
import com.springboot.todo.Entity.User;
import com.springboot.todo.Service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Controller
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService){
        this.todoService=todoService;
    }

    @PostMapping("/todo/{id}")
    public Long saveTodo(@PathVariable String id, @RequestBody TodoSaveRequestDto todoSaveRequestDto){
        Long saveTodo = todoService.saveTodo(id,todoSaveRequestDto);
        return saveTodo;
    }

    @GetMapping("/todo/{id}")
    public Optional<Todo> todo(@PathVariable Long id){
        Optional<Todo> todo = todoService.findById(id);
        return todo;
    }

    @PatchMapping("/todo/{id}")
    public void editTodo(@PathVariable Long id, @RequestBody TodoEditRequestDto todoEditRequestDto){
        todoService.editTodo(id,todoEditRequestDto);
    }

    @DeleteMapping("/todo/{id}")
    public void deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
    }

}
