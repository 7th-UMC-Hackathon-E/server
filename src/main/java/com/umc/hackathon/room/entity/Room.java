package com.umc.hackathon.room.entity;

import com.umc.hackathon.global.baseEntity.BaseEntity;
import com.umc.hackathon.user.entity.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name="room")
@Entity
public class Room extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String name;

    private String clusterName;

    private Long numberOfPeople;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserStudy> studies = new ArrayList<>();

    public Long getRoomId() {
        return roomId;
    }

    public Long getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Long numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}
