package com.springboot.todo.service;

import com.springboot.todo.entity.Todo;
import com.springboot.todo.entity.User;
import com.springboot.todo.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public void validate(Todo todo){
        if(todo == null){
            log.warn("Entity can not be null");
            throw new IllegalStateException("Entity can not be null");
        }if(todo.getUser() == null){
            log.warn("Unknown User");
            throw new IllegalStateException("Unknown User");
        }
    }

    public List<Todo> todoCreate(Todo todo){
        try{
            validate(todo);
            todoRepository.save(todo);
            log.info("Todo Id: {} is saved", todo.getId());
            return retrieve(todo.getUser());
        }catch (Exception e){
            log.error("An error occurred while creating a todo", todo.getId(), e);
            throw new RuntimeException("An error occurred while creating a todo");
        }
    }

    public List<Todo> retrieve(User user){
        List<Todo> list =  todoRepository.findByUser(user);
        if(list != null){
            return list;
        }else{
            log.warn("Todo Table is empty");
            throw new IllegalStateException("Todo Table is empty");
        }
    }

    public List<Todo> todoUpdate(Todo todo){
        validate(todo);
        Optional<Todo> original = todoRepository.findById(todo.getId());
        original.ifPresentOrElse( (newTodo) -> {
            newTodo.setItem(todo.getItem());
            newTodo.setDone(todo.isDone());
            newTodo.setUpdatedDate(LocalDateTime.now());
            todoRepository.save(newTodo);
            }, () -> {
                log.warn("Entity is not existed");
                throw new NoSuchElementException("Entity is not existed");
            }
        );
        return retrieve(todo.getUser());
    }

    public List<Todo> todoDelete(Todo todo){
        try{
            todoRepository.delete(todo);
        }catch (Exception e){
            log.error("An error occurred while deliting a Todo", todo.getId(), e);
            throw new RuntimeException("An error occurred while deliting a todo" + todo.getId(), e);
        }
        return retrieve(todo.getUser());
    }
}
