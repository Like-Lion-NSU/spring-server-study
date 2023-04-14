package com.springboot.todo.service;

import com.springboot.todo.dto.TodoRequestDto;
import com.springboot.todo.dto.TodoResponseDto;
import com.springboot.todo.entity.Todo;
import com.springboot.todo.repository.TodoRepository;
import com.springboot.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TodoServiceImpl implements TodoService{
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    @Override
    public List<TodoResponseDto> retrieveTodos(String userId) {
        List<Todo> todoList = todoRepository.findByUser_Id(userId);
        List<TodoResponseDto> dtoList = todoList.stream().map(TodoResponseDto::new).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public TodoResponseDto retrieveTodo(Long id) throws NoSuchElementException{
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 투두입니다."));
        TodoResponseDto dto = new TodoResponseDto(todo);
        return dto;
    }

    @Override
    public Long saveTodo(String userId, TodoRequestDto dto) {
        log.info(userId);
        Todo todo = TodoRequestDto.toEntity(dto);
        todo.setUser(userRepository.findByUserId(userId));
        todo.setCreatedDate(LocalDateTime.now());
        todoRepository.save(todo);
        return todo.getId();
    }

    @Override
    public void updateTodo(Long id, TodoRequestDto dto) throws NoSuchElementException{
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 투두입니다."));
        todo.setItem(dto.getItem());
        todo.setDone(dto.isDone());
        todo.setUpdatedDate(LocalDateTime.now());
        todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));
        todoRepository.deleteById(todo.getId());
    }
}
