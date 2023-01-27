package com.springboot.todo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uid;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "user")
    private List<Todo> todo = new ArrayList<>();

//    @ElementCollection(fetch = FetchType.EAGER)
//    private List<String> roles = new ArrayList<>();
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorites(){
//        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
//    }
//
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Override
//    public String getUsername() {
//        return this.uid;
//    }
//
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Override
//    public boolean isAccountNonExpired(){
//        return true;
//    }
//
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Override
//    public boolean isAccountNonLocked(){
//        return true;
//    }
//
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Override
//    public boolean isCredentialsNonExpired(){
//        return true;
//    }
//
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Override
//    public boolean isEnabled(){
//        return true;
//    }
}
