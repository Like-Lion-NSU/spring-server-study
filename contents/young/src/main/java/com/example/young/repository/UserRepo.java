package com.example.young.repository;

import com.example.young.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo {
    User save(User contents);
    Optional<User> findByID(Long ID);
    Optional<User> findByloginId(String loginId);
    List<User> findAll();
}
