package com.example.young.entity;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;




public interface UserDetails extends Serializable {

    Collection<? extends GrantedAuthority> getAuthorities();  // getAuthorities() : 계정이 가지고 있는 권한 목록 리턴

    String getPassword();       // 비밀번호 리턴

    String getUsername();       // 아이디 리턴

    boolean isAccountNonExpried();      // 계정 만료 여부(true - 만료되지 않음)

    boolean isAccountNonLocked();       // 계정 잠김 여부(true - 잠기지 않음)

    boolean isCredentialsNonExpried();      // 비밀번호 만료 여부(ture - 만료되지 않음)

    boolean isEnabled();        // 계정 활성 여부(true - 활성 됨)


}
