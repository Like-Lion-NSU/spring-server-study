package com.example.young.repository;

import com.example.young.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepoJPA extends JpaRepository<Todo, Long> {
}
