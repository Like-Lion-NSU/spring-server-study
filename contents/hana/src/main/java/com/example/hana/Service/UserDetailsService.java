package com.example.hana.Service;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.hana.Dto.UserDetails;

@Service
public interface UserDetailsService {
    //username(식별자)을 가지고 UserDetails 객체를 리턴
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException;

}
