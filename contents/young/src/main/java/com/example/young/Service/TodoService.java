package com.example.young.Service;

import com.example.young.dto.TodoDto;
import com.example.young.entity.Todo;
import com.example.young.entity.User;
import com.example.young.repository.TodoRepoJPA;
import com.example.young.repository.UserRepoJPA;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepoJPA todoRepoJPA;
    private final UserRepoJPA userRepoJPA;
    @Autowired
    private TodoService(TodoRepoJPA todoRepoJPA, UserRepoJPA userRepoJPA){
        this.todoRepoJPA=todoRepoJPA;
        this.userRepoJPA=userRepoJPA;
    }

    // save 로직
    public Long save_todo(String userId, TodoDto todoDto){
        User user = userRepoJPA.findByUserId(userId);
        Todo todo = todoDto.toEntity();
        todo.setUser(user);
        todoRepoJPA.save(todo);
        return todo.getId();
    }

    public Optional<Todo> findById(Long id) {
        return todoRepoJPA.findById(id);
    }

    public void update_todo(Long id, TodoDto todoDto){
        Todo todo = todoRepoJPA.findById(id).get();
        todo.setItem(todoDto.getItem());
        todo.setId(todoDto.getId());
        todo.setUpdatedDate(todoDto.getUpdatedDate());
        todoRepoJPA.save(todo);
    }

    public void delete_todo(Long id){
        todoRepoJPA.deleteById(id);
    }
}
