package com.example.hana.Dto;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public interface UserDetails extends Serializable {

    Collection<? extends GrantedAuthority> getAuthorities(); //계정이 가지고 있는 권한 목록을 리턴

    String getUsername();//계정의 이름을 리턴

    String getPassword();//비번 리턴

    boolean isAccountNonExpired();//계정이 만료됐는지를 리턴

    boolean isAccountNonLocked();//계정이 잠겨있는지를 리턴

    boolean isCredentialsNonExpired();//비번 만료 리턴

    boolean isEnabled();//계정 활성화 리턴


}
