package com.umc.hackathon.review.entity;

import com.umc.hackathon.global.baseEntity.BaseEntity;
import com.umc.hackathon.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(nullable = true, length = 100)
    private String content;

    private Double progress;

    private Integer totalTodos;

    private Integer completedTodos;

    public Review(User user, String content, Double progress, Integer numberAll, Integer numberDone) {
        super();
    }
}