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

import java.util.List;
import java.util.Optional;

@RestController
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

    @GetMapping("/todos{id}")
    public List<Todo> findTodos(@PathVariable Long id){ //리스트 형식으로 리턴
        List<Todo> todos = todoService.findTodos(id); //리스트 형식인 자료형 변수에 userService의 findUsers 메소드 리턴 값을 users에 저장
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
