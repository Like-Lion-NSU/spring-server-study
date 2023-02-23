package com.springboot.todo.controller;

import com.springboot.todo.dto.TodoRequestDTO;
import com.springboot.todo.dto.TodoResponseDTO;
import com.springboot.todo.entity.Todo;
import com.springboot.todo.entity.User;
import com.springboot.todo.service.TodoService;
import com.springboot.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private TodoService todoService;
    private UserService userService;

    @Autowired
    public TodoController(TodoService todoService, UserService userService){
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping
    public List<TodoResponseDTO> retrieveTodoList(@AuthenticationPrincipal String userId){
        User user = userService.retrieveByUserId(userId);
        List<Todo> todos = todoService.retrieveTodoList(user.getId());
        List<TodoResponseDTO> dtos = todos.stream().map(TodoResponseDTO::new).collect(Collectors.toList());
        return dtos;
    }

    @PostMapping
    public Long saveTodo(@AuthenticationPrincipal String userId, @RequestBody TodoRequestDTO dto){
        Todo todo = TodoResponseDTO.toEntity(dto);
        todo.setId(null);
        todo.setUser(userService.retrieveByUserId(userId));
        todo.setCreatedDate(LocalDateTime.now());
        return todoService.saveTodo(todo);
    }

    @GetMapping("/{id}")
    public TodoResponseDTO retrieveTodo(@AuthenticationPrincipal String userId, @PathVariable Long id){
        Todo todo =  todoService.retrieveTodo(id).get();
        TodoResponseDTO dto = new TodoResponseDTO(todo);
        return dto;
    }

    @PutMapping("/{id}")
    public void updateTodo(@AuthenticationPrincipal String userId, @PathVariable Long id, @RequestBody TodoRequestDTO dto){
        Todo todo = todoService.retrieveTodo(id).get();
        todo.setItem(dto.getItem());
        todo.setDone(dto.isDone());
        todo.setUpdatedDate(LocalDateTime.now());
        todoService.updateTodo(todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@AuthenticationPrincipal String userId, @PathVariable Long id){
        todoService.deleteTodo(id);
    }
}
