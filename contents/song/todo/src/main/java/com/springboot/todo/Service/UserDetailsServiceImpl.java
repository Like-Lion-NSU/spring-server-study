package com.springboot.todo.Service;

import com.springboot.todo.Dto.UserDetails;
import com.springboot.todo.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username){
        log.info("[loadUserByUsername] loadUserByUsername 수행. username : {}", username);
        return userRepository.findByUserId(username).get();
    }
}
