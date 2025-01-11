package com.umc.hackathon.room.dto;

import com.umc.hackathon.room.entity.Room;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoomListDTO {
    List<Room> rooms;
}
