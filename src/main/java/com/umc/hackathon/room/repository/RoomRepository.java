package com.umc.hackathon.room.repository;

import com.umc.hackathon.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
