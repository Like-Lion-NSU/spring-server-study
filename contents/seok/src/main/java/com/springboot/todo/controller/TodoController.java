package com.springboot.todo.controller;

import com.springboot.todo.dto.ResponseDTO;
import com.springboot.todo.dto.TodoDTO;
import com.springboot.todo.entity.Todo;
import com.springboot.todo.entity.User;
import com.springboot.todo.service.TodoService;
import com.springboot.todo.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo")
@Tag(name = "Todo", description = "Todo API Document")
public class TodoController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
        try{
            User user = userService.getUser(userId);
            List<Todo> todos = todoService.retrieve(user);
            List<TodoDTO> dtos = todos.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        }catch (IllegalStateException e){
            ResponseDTO response = ResponseDTO.builder().error("Todo Table is empty").build();
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            ResponseDTO response = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping
    public ResponseEntity<?> createTodoList(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
        try{
            User user = userService.getUser(userId);
            Todo todo = TodoDTO.toEntity(dto);
            todo.setId(null);
            todo.setUser(user);
            todo.setCreatedDate(LocalDateTime.now());

            List<Todo> todos = todoService.todoCreate(todo);
            List<TodoDTO> dtos = todos.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            ResponseDTO response = ResponseDTO.builder().error("An unexpected error occurred").build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTodoList(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
        try{
            User user = userService.getUser(userId);
            Todo todo = TodoDTO.toEntity(dto);
            todo.setUser(user);

            List<Todo> todos = todoService.todoUpdate(todo);
            List<TodoDTO> dtos = todos.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        }catch (IllegalStateException e){
            ResponseDTO response = ResponseDTO.builder().error("Entity is not existed").build();
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            ResponseDTO response = ResponseDTO.builder().error("An unexpected error occurred").build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodoList(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
        try{
            User user = userService.getUser(userId);
            Todo todo = TodoDTO.toEntity(dto);
            todo.setUser(user);

            List<Todo> todos = todoService.todoDelete(todo);
            List<TodoDTO> dtos = todos.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e) {
            ResponseDTO response = ResponseDTO.builder().error("An error occurred while deleting a Todo").build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
