package com.example.young.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;        // 식별 키

    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(min=6, max=12, message = "아이디는 2자 이상 12자 이하입니다.")
    private String userId;      // 사용자 Id

    @Column(nullable = false)
    @NotBlank
//    @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 사용하세요.")
    private String password;    // 사용자 password

    @Column(nullable = false)
    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,4}$", message = "이름은 특수문자를 제외한 2~4자리여야 합니다.")
    private String name;        // 사용자 이름

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Todo> todoList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)     // 값 타입 컬랙션 매핑 때 쓰는 어노테이션, 해당 필드가 컬렉션 객체임을 JAP에게 알려줌
    private List<String> roles = new ArrayList<>();

//    @Builder
//    public User(String userId, String name, String password, List<Todo> todoList, String role) {
//        this.userId = userId;
//        this.name = name;
//        this.password = password;
//        this.todoList = todoList;
//        this.roles.add(role);
//    }

    /* Interface UserDetails Override */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.userId;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpried() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpried() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
