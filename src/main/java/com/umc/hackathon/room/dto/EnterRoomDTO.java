package com.umc.hackathon.room.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EnterRoomDTO {
    private Long userId;
    private Long roomId;
}
