package com.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;
    @Column(length = 200)
    private String title;
    @Column
    private Boolean Completed = false; //완료 여부
    @Column
    private String dueDate;  //마감일


}
