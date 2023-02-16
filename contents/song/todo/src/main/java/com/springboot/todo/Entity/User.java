package com.springboot.todo.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User /*implements UserDetails*/ {
    //시스템 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //사용자 id
    @Column(nullable = false, unique = true)
    private String userId;

    //사용자 비밀번호
    @Column(nullable = false)
    private String password;

    //사용자 이름
    @Column(nullable = false)
    private String name;

    //todoList
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Todo> todoList = new ArrayList<>();

    //역할
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    /*@Builder가 만들어주는 7가지

1. 내부 정적 클래스(FooBuilder)

2. private 필드(Builder의 각 파라미터에 해당하는 필드, static X, final X)

3. pacakge private(접근제한자:default)의 아규먼트가 없는 빈 생성자

4. Builder의 각 파라미터에 대해, setter와 비슷한 메서드 - 파라미터와 같은 타입, 같은 이름. builder 자신을 반환

5. build() 메서드 - FooClass의 생성자를 호출하여 FooClass 객체 반환

6. toString()

7. builder() 메서드 - builder의 새 인스턴스를 만들어서 반환*/
    @Builder
    public User(String userId, String name, String password, List<Todo> todoList, String role){
        this.userId=userId;
        this.name=name;
        this.password=password;
        this.todoList=todoList;
        this.roles.add(role);
//        this.role=role;
    }
}
