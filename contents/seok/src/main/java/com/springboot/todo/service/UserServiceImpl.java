package com.springboot.todo.service;

import com.springboot.todo.entity.User;
import com.springboot.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User retrieveById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User retrieveByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public User create(User user) {
        if (userRepository.existsByUserId(user.getUserId())) {
            log.warn("UserId already exists");
            throw new RuntimeException("UserId already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public User getByCredentials(String userId, String password, PasswordEncoder encoder) {
        final User user = userRepository.findByUserId(userId);
        if (user != null && encoder.matches(password, user.getPassword())) {
            return userRepository.findByUserIdAndPassword(userId, user.getPassword());
        } else {
            return null;
        }
    }

    @Override
    public List<User> retrieveAll() {
        return userRepository.findAll();
    }

    @Override
    public void update(User user) {
        User original = userRepository.findByUserId(user.getUserId());
        original.setUserName(user.getUserName());
        original.setPassword(user.getPassword());
        userRepository.save(original);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
