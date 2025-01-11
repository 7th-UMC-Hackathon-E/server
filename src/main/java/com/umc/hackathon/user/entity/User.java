package com.umc.hackathon.user.entity;

import com.umc.hackathon.global.baseEntity.BaseEntity;
import com.umc.hackathon.room.entity.UserStudy;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Table(name="user")
@Entity
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate birth;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Zodiac zodiac;
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private UserStudy userStudy;

    public void setName(String name) {
        this.name = name;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void setZodiac(Zodiac zodiac) {
        this.zodiac = zodiac;
    }

}
