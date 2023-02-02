package com.springboot.todo.entity;

public interface UserDetails {
    Collection<? extends GrantedAuthority> getAuthorites();

    String getPassword();

    String getUsername();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();

    boolean isEnabled();
}
