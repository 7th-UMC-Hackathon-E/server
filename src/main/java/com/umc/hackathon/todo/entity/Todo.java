package com.umc.hackathon.todo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import com.umc.hackathon.global.baseEntity.BaseEntity;

@Entity
@Getter
@Table(name = "todo")
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private boolean status;

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
