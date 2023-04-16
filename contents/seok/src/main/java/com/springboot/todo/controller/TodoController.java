package com.springboot.todo.controller;

import com.springboot.todo.dto.TodoRequestDto;
import com.springboot.todo.dto.TodoResponseDto;
import com.springboot.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService){
        this.todoService=todoService;
    }

    @GetMapping
    public List<TodoResponseDto> retrieveTodos(@AuthenticationPrincipal UserDetails userDetails){
        List<TodoResponseDto> dtos = todoService.retrieveTodos(userDetails.getUsername());
        return dtos;
    }

    @PostMapping
    public Long saveTodo(@AuthenticationPrincipal UserDetails userDetails, @Validated @RequestBody TodoRequestDto todoRequestDto, Errors errors){
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = (User) principal;
        Long todoId = todoService.saveTodo(userDetails.getUsername(), todoRequestDto);
        if(errors.hasErrors()) {

        }
        return todoId;
    }

    @GetMapping("/{id}")
    public TodoResponseDto retrieveTodo(@PathVariable Long id){
        try {
            TodoResponseDto dto = todoService.retrieveTodo(id);
            return dto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public void updateTodo(@PathVariable Long id, @Validated @RequestBody TodoRequestDto todoRequestDto, Errors errors){
        if(errors.hasErrors()) {

        }
        todoService.updateTodo(id,todoRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteTodo(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
