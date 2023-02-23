package com.springboot.todo.service.old;

import com.springboot.todo.entity.User;
import com.springboot.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User retrieveById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow( () -> {
            log.info("User is not existed");
            throw new NoSuchElementException("User is not existed");
        });
    }

    public User retrieveByUserId(String userId) {
        User user = userRepository.findByUserId(userId);
        return user;
    }

    public User create(User user) {
        if (user == null || user.getUserId() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        final String userId = user.getUserId();
        if (userRepository.existsByUserId(userId)) {
            log.warn("UserId already exists");
            throw new RuntimeException("UserId already exists");
        }
        return userRepository.save(user);
    }

    public User getByCredentials(final String userId, final String password, final PasswordEncoder encoder) {
        final User user = userRepository.findByUserId(userId);
        if (user != null && encoder.matches(password, user.getPassword())) {
            return userRepository.findByUserIdAndPassword(userId, user.getPassword());
        } else {
            return null;
        }
    }

    public List<User> retrieveAll() {
        return userRepository.findAll();
    }

    public User update(User user) {
        Optional<User> original = userRepository.findById(user.getId());
        original.ifPresentOrElse( (newUser) -> {
            newUser.setUserName(user.getUserName());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(newUser);
                }, () -> {
                    log.warn("Entity is not existed");
                    throw new NoSuchElementException("Entity is not existed");
                }
        );
        return retrieveByUserId(user.getUserId());
    }

    public void delete(User user) {
        try{
            userRepository.delete(user);
        }catch (Exception e){
            log.error("An error occurred while deliting a User", user.getId(), e);
            throw new RuntimeException("An error occurred while deliting a user" + user.getId(), e);
        }
    }
}
