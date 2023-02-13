package com.springboot.todo.repository;

import com.springboot.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);
    boolean existsByUserId(String userId);
    User findByUserIdAndPassword(String userId, String password);

}
