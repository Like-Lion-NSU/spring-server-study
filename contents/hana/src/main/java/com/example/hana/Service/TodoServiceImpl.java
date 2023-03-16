package com.example.hana.Service;


import com.example.hana.Dto.TodoEditRequestDto;
import com.example.hana.Entity.Todo;
import com.example.hana.Entity.User;
import com.example.hana.Dto.TodoSaveRequestDto;
import com.example.hana.Repository.TodoRepository;
import com.example.hana.Repository.UserRepository;
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

    @Override
    public List<Todo> findTodos(Long id){
        return todoRepository.findByUser_Id(id);
    }

    @Override
    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }

    @Override
    public void editTodo(Long id, TodoEditRequestDto todoEditRequestDto) {
        Todo todo = todoRepository.findById(id).orElse(null);
        todo.setIsDone(todoEditRequestDto.getIsDone());
        todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}