package com.example.young.Service;

import com.example.young.dto.TodoEditRequestDto;
import com.example.young.dto.TodoSaveRequestDto;
import com.example.young.entity.Todo;
import com.example.young.entity.User;
import com.example.young.repository.TodoRepoJPA;
import com.example.young.repository.UserRepoJPA;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TodoServiceImpl implements TodoService{

    private final TodoRepoJPA todoRepoJPA;
    private final UserRepoJPA userRepoJPA;

    public TodoServiceImpl(TodoRepoJPA todoRepoJPA, UserRepoJPA userRepoJPA) {
        this.todoRepoJPA = todoRepoJPA;
        this.userRepoJPA = userRepoJPA;
    }

    @Override
    public Long saveTodo(String id, TodoSaveRequestDto todoSaveRequestDto) {    // todo 생성
        User user = userRepoJPA.findByUserId(id).orElse(null);
        Todo todo = todoSaveRequestDto.toEntity();
        todo.setUser(user);
        todoRepoJPA.save(todo);
        return todo.getId();
    }

    @Override
    public List<Todo> findTodos(Long id) {
        return todoRepoJPA.findByUser_Id(id);
    }    // todo 전체 조회

    @Override
    public Optional<Todo> findById(Long id) {
        return todoRepoJPA.findById(id);
    }    // todo 조회

    @Override
    public void editTodo(Long id, TodoEditRequestDto todoEditRequestDto) {    // todo 수정
        Todo todo = todoRepoJPA.findById(id).orElse(null);
        todo.setIsDone(todoEditRequestDto.getIsDone());
        todoRepoJPA.save(todo);
    }

    @Override
    // todo 삭제
    public void deleteTodo(Long id) {
        todoRepoJPA.deleteById(id);
    }
}
