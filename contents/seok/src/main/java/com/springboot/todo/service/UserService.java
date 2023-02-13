package com.springboot.todo.service;

import com.springboot.todo.entity.User;
import com.springboot.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUser(String userId){
        User user = userRepository.findByUserId(userId);
        return user;
    }
    public User createUser(User user){
        if(user == null || user.getUserId() == null){
            throw new RuntimeException("Invalid arguments");
        }
        final String userId = user.getUserId();
        if(userRepository.existsByUserId(userId)){
            log.warn("UserId already exists");
            throw new RuntimeException("UserId already exists");
        }
        return userRepository.save(user);
    }

    public User getByCredentials(final String userId, final String password, final PasswordEncoder encoder){
        final User user = userRepository.findByUserId(userId);
        if(user != null && encoder.matches(password, user.getPassword())){
            return userRepository.findByUserIdAndPassword(userId, user.getPassword());
        }else{
            return null;
        }
    }
}
