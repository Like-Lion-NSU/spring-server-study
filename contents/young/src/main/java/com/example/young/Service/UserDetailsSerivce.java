package com.example.young.Service;

import com.example.young.entity.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsSerivce {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /* UserDetails는 Spring Security에서 제공되는 개념
     *   UserDetails의 username은 각 사용자를 구분할 수 있는 ID 의미  */
}
