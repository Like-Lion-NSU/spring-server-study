package com.springboot.todo.Repository;

import com.springboot.todo.entity.Todo;
import com.springboot.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> { }
