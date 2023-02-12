package com.example.young.Controller;

import com.example.young.Service.TodoService;
import com.example.young.dto.TodoDto;
import com.example.young.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Controller
@RequestMapping("/todo")
public class TodoController {
    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @PostMapping("/{userId}")
    public Long save_todo(@PathVariable String userId, @RequestBody TodoDto todoDto){
        Long save_todo=todoService.save_todo(userId, todoDto);
        return save_todo;
    }

    @PutMapping("/{id}")
    public void update_todo(@PathVariable Long id, @RequestBody TodoDto todoDto){
        todoService.update_todo(id, todoDto);
    }

    @DeleteMapping("/{id}")
    public void delete_todo(@PathVariable Long id){
        todoService.delete_todo(id);
    }

    @GetMapping("/{id}")
    public Optional<Todo> todo(@PathVariable Long id){
        Optional<Todo> todo = todoService.findById(id);
        return todo;
    }
}
