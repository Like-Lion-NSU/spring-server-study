package com.example.young.repository;

import com.example.young.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUserId(String userId);
    List<User> findAll();
}
