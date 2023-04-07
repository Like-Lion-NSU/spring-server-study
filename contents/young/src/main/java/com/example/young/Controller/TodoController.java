package com.example.young.Controller;


import com.example.young.dto.TodoSaveRequestDto;
import com.example.young.dto.TodoEditRequestDto;
import com.example.young.entity.Todo;
import com.example.young.entity.User;
import com.example.young.Service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class TodoController {
    private TodoService todoService;

    @Autowired  // 생성자
    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @PostMapping("/todo/{id}")      // 일정 추가
    public Long saveTodo(@PathVariable String id, @RequestBody TodoSaveRequestDto todoSaveRequestDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User)principal;
        Long saveTodo = todoService.saveTodo(id,todoSaveRequestDto);
        return saveTodo;
    }

    @GetMapping("/todos/{id}")      // 일정 가져오기
    public List<Todo> findTodos(@PathVariable Long id){
        List<Todo> todos = todoService.findTodos(id);
        return todos;
    }

    @GetMapping("/todo/{id}")       //
    public Optional<Todo> todo(@PathVariable Long id){
        Optional<Todo> todo = todoService.findById(id);
        return todo;
    }

    @PatchMapping("/todo/{id}")     // 일정수정
    public void editTodo(@PathVariable Long id, @RequestBody TodoEditRequestDto todoEditRequestDto){
        todoService.editTodo(id, todoEditRequestDto);
    }

    @DeleteMapping("/todo/{id}")        // 일정 삭제
    public void deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
    }


}
