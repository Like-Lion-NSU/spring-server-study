package com.springboot.todo.Service;

import com.springboot.todo.Dto.TodoEditRequestDto;
import com.springboot.todo.Dto.TodoSaveRequestDto;
import com.springboot.todo.Entity.Todo;
import com.springboot.todo.Entity.User;
import com.springboot.todo.Repository.TodoRepository;
import com.springboot.todo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Long saveTodo(String id, TodoSaveRequestDto todoSaveRequestDto) { //todo pk값 리턴
//        try {
            User user = userRepository.findByUserId(id).orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다.")); //유저 아이디로 유저 dao에서 찾음
            Todo todo = todoSaveRequestDto.toEntity(); //dto에 있는 toEntity로 값 초기화 시켜줌
            todo.setUser(user); //todo 유저에 유저 정보 저장
            todoRepository.save(todo); //todo dao에 저장
            return todo.getId();
//        }catch(Exception e){
//            e.printStackTrace();
//            throw e;
//        }
    }
    //Todo 전체 조회
    @Override
    public List<Todo> findTodos(Long id){ //List로 todo 조회 결과 받음
//        try {
        todoRepository.findById(id).orElseThrow(() -> new RuntimeException("리스트에 없는 할 일 입니다."));
            return todoRepository.findByUser_Id(id); // todo 외래키로 todo 조회
//        }catch(Exception e){
//            e.printStackTrace();
//            throw e;
//        }
        }

    //Todo 특정 조회
    @Override
    public Optional<Todo> findById(Long id) { //기본키로 특정 todo 조회
//        try {
            todoRepository.findById(id).orElseThrow(() -> new RuntimeException("리스트에 없는 할 일 입니다."));
            return todoRepository.findById(id);
//        }catch(Exception e){
//            e.printStackTrace();
//            throw e;
//        }
    }

    //Todo 수정
    @Override
    public void editTodo(Long id, TodoEditRequestDto todoEditRequestDto) {
//        try {
            Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("리스트에 없는 할 일 입니다."));
            todo.setIsDone(todoEditRequestDto.getIsDone());
            todoRepository.save(todo);
//        }catch(Exception e){
//            e.printStackTrace();
//            throw e;
//        }
    }

    //Todo 삭제
    @Override
    public void deleteTodo(Long id) {
//        try {
        todoRepository.findById(id).orElseThrow(() -> new RuntimeException("리스트에 없는 할 일 입니다."));
            todoRepository.deleteById(id);
//        }catch(Exception e){
//            e.printStackTrace();
//            throw e;
//        }
    }
}
