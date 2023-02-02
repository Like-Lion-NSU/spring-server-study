package com.repository;

import com.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo {
    User save(User user);
    Optional<User> findById(String userId);
    Optional<User> findByName(Long id);

    Optional<User> findById(Long id);

    Optional<User> findByUserId(String userId);

    List<User> findAll();
}
