package com.springboot.todo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String uid;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Todo> todo = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorites(){
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.uid;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled(){
        return true;
    }
}
