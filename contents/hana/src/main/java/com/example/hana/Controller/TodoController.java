package com.example.hana.Controller;

import com.example.hana.Dto.TodoEditRequestDto;
import com.example.hana.Dto.TodoSaveRequestDto;
import com.example.hana.Entity.Todo;
import com.example.hana.Entity.User;
import com.example.hana.Service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService){
        this.todoService=todoService;
    }

    @PostMapping("/todo")
    public Long saveTodo( @RequestBody TodoSaveRequestDto todoSaveRequestDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User)principal;
        Long saveTodo = todoService.saveTodo(user.getUserId(),todoSaveRequestDto);
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
        todoService.editTodo(id,todoEditRequestDto);
    }

    @DeleteMapping("/todo/{id}")
    public void deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
    }
}
