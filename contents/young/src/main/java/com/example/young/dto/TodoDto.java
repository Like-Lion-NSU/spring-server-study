package com.example.young.dto;


import com.example.young.entity.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class TodoDto {
    private Long id;
    private String item;
    private Boolean isDone;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public TodoDto(Long id, String item, Boolean isDone, LocalDateTime createdDate, LocalDateTime updatedDate){
        this.id= id;
        this.item=item;
        this.isDone=isDone;
        this.createdDate=createdDate;
        this.updatedDate=updatedDate;
    }

    public Todo toEntity(){
        return Todo.builder()
                .id(id)
                .item(item)
                .isDone(isDone)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .build();
    }

}
