package com.example.hana.Service;


import com.example.hana.Dto.TodoEditRequestDto;
import com.example.hana.Entity.Todo;
import com.example.hana.Entity.User;
import com.example.hana.Dto.TodoSaveRequestDto;
import com.example.hana.Repository.TodoRepository;
import com.example.hana.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    //생성자를 통해 의존성 주입 ->RequiredArgsConstructor 어노테이션 사용
//    @Autowired
//    public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository){
//        this.todoRepository=todoRepository;
//        this.userRepository=userRepository;
//    }

    //todo생성
    @Override
    public Long saveTodo(String id, TodoSaveRequestDto todoSaveRequestDto) {
        User user = userRepository.findByUserId(id).get();
        Todo todo = todoSaveRequestDto.toEntity();
        todo.setUser(user);
        todoRepository.save(todo);
        return todo.getId();
    }
    //todo전체조회
    @Override
    public List<Todo> findTodos(Long id){
        return todoRepository.findByUser_Id(id);
    }
   //todo조회
    @Override
    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }
   //todo수정
    @Override
    public void editTodo(Long id, TodoEditRequestDto todoEditRequestDto) {
        Todo todo = todoRepository.findById(id).orElse(null);
        todo.setIsDone(todoEditRequestDto.getIsDone());
        todoRepository.save(todo);
    }
   //todo삭제
    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}