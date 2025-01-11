package com.umc.hackathon.room.repository;

import com.umc.hackathon.room.entity.Room;
import com.umc.hackathon.room.entity.UserStudy;
import com.umc.hackathon.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserStudyRepository extends JpaRepository<UserStudy, Long> {
    List<UserStudy> findAllByRoom(Room room);
    Optional<UserStudy> findByUser(User user);
}
