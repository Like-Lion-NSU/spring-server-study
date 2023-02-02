package com.springboot.todo.Repository;

import com.springboot.todo.Entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUid(String uid);
    List<User> findAll();

}
