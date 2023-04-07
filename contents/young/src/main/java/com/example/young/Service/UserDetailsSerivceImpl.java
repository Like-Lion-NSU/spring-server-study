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
    // UserDetailsServiceImpl.class에 Loggin을 주입함
    private final UserRepoJPA userRepoJPA;  // UserRepoJPA 객체 생성
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. username : {}", username);
        return userRepoJPA.findByUserId(username).get();
        // UserRepoJPA에서 파라미터 username을 UserId값으로 일치 값을 찾아 객체를 리턴
    }
}
