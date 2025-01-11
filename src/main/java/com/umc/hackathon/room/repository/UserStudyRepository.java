package com.umc.hackathon.room.repository;

import com.umc.hackathon.room.entity.Room;
import com.umc.hackathon.room.entity.UserStudy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStudyRepository extends JpaRepository<UserStudy, Long> {
    List<UserStudy> findAllByRoom(Room room);
}
