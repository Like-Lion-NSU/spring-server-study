package com.springboot.todo.Service;

import com.springboot.todo.Dto.TodoEditRequestDto;
import com.springboot.todo.Dto.TodoSaveRequestDto;
import com.springboot.todo.Entity.Todo;
import com.springboot.todo.Entity.User;
import com.springboot.todo.Repository.TodoRepository;
import com.springboot.todo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository){
        this.todoRepository=todoRepository;
        this.userRepository=userRepository;
    }

    @Override
    public Long saveTodo(String id, TodoSaveRequestDto todoSaveRequestDto) {
        User user = userRepository.findByUserId(id).get();
        Todo todo = todoSaveRequestDto.toEntity();
        todo.setUser(user);
        todoRepository.save(todo);
        return todo.getId();
    }


    //Todo 특정 조회
    @Override
    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }

    //Todo 수정
    @Override
    public void editTodo(Long id, TodoEditRequestDto todoEditRequestDto) {
        Todo todo = todoRepository.findById(id).orElse(null);
        todo.setIsDone(todoEditRequestDto.getIsDone());
        todoRepository.save(todo);
    }

    //Todo 삭제
    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
