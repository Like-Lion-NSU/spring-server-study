package com.example.young.repository;

import com.example.young.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepoJPA extends JpaRepository<Todo, Long> {
    List<Todo> findByUser_Id(Long id);      // List<Todo> 형식으로 findByUser_Id(Loing id)를 리턴함
}
