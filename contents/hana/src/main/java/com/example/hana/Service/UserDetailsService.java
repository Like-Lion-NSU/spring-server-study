package com.example.hana.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserDetailsService {
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException;
    //파라미터로 username을 받고 리턴값으로 userdetails를 돌려줌
}
