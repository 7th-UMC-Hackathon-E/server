package com.umc.hackathon.room.entity;

import com.umc.hackathon.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name="user_study")
public class UserStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double progress;

    private Integer numberDone;

    private Integer numberAll;

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public void setNumberDone(Integer numberDone) {
        this.numberDone = numberDone;
    }

    public void setNumberAll(Integer numberAll) {
        this.numberAll = numberAll;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public Double getProgress() {
        return progress;
    }
}
