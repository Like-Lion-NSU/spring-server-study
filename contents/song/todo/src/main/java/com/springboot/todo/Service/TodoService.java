package com.springboot.todo.Service;

import com.springboot.todo.Dto.TodoEditRequestDto;
import com.springboot.todo.Dto.TodoSaveRequestDto;
import com.springboot.todo.Entity.Todo;
import com.springboot.todo.Entity.User;

import java.util.List;
import java.util.Optional;

public interface TodoService {

    //todo 생성
    public Long saveTodo(String id, TodoSaveRequestDto todoSaveRequestDto);

    //Todo 전체 조회
    public List<Todo> findTodos(Long id);

    //todo 조회
    public Optional<Todo> findById(Long id);

    //todo 수정
    public void editTodo(Long id, TodoEditRequestDto todoEditRequestDto);

    //todo 삭제
    public void deleteTodo(Long id);
}
