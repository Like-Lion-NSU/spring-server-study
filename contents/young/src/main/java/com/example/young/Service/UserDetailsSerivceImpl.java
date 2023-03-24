package com.example.young.Service;

import com.example.young.entity.UserDetails;
import com.example.young.repository.UserRepoJPA;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsSerivceImpl implements UserDetailsSerivce{

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsSerivceImpl.class);

    private final UserRepoJPA userRepoJPA;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. username : {}", username);
        return userRepoJPA.findByUserId(username).get();
    }
}
