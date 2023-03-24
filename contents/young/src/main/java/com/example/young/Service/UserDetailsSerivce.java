package com.example.young.Service;

import com.example.young.entity.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsSerivce {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
