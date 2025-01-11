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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = true, length = 100)
    private String content;

    private Double progress;

    private Integer numberAll;

    private Integer numberDone;

    public Review(User user, String content, Double progress, Integer numberAll, Integer numberDone) {
        this.user = user;
        this.content = content;
        this.progress = progress;
        this.numberAll = numberAll;
        this.numberDone = numberDone;
    }
}