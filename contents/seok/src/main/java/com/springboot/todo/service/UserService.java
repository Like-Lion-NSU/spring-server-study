package com.springboot.todo.service;

import com.springboot.todo.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface UserService {
    User retrieveById(Long id);
    User retrieveByUserId(String userId);

    User create(User user);
    User getByCredentials(final String userId, final String password, final PasswordEncoder encoder);
    List<User> retrieveAll();
    void update(User user);
    void delete(Long id);
}
