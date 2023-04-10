package com.springboot.todo.Controller;

import com.springboot.todo.Dto.TodoEditRequestDto;
import com.springboot.todo.Dto.TodoSaveRequestDto;
import com.springboot.todo.Entity.Todo;
import com.springboot.todo.Entity.User;
import com.springboot.todo.Service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import java.util.List;
import java.util.Optional;

//@RestControllerAdvice
//@ControllerAdvice
@RestController
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService){
        this.todoService=todoService;
    }

    @PostMapping("/todo")
    public Long saveTodo(@RequestBody TodoSaveRequestDto todoSaveRequestDto){
        try {

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = (User) principal;
            Long saveTodo = todoService.saveTodo(user.getUserId(), todoSaveRequestDto);
            return saveTodo;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/todos/{id}")
    public List<Todo> findTodos(@PathVariable Long id){ //리스트 형식으로 리턴
        try {
            List<Todo> todos = todoService.findTodos(id); //리스트 형식인 자료형 변수에 userService의 findUsers 메소드 리턴 값을 users에 저장
            return todos;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/todo/{id}")
    public Optional<Todo> todo(@PathVariable Long id){
        try {
            Optional<Todo> todo = todoService.findById(id);
            return todo;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @PatchMapping("/todo/{id}")
    public void editTodo(@PathVariable Long id, @RequestBody TodoEditRequestDto todoEditRequestDto){
        try {
            todoService.editTodo(id, todoEditRequestDto);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @DeleteMapping("/todo/{id}")
    public void deleteTodo(@PathVariable Long id){
        try {
            todoService.deleteTodo(id);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
