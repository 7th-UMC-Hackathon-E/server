package com.umc.hackathon.room.dto;

import com.umc.hackathon.todo.entity.Todo;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoomDTO {
    private List<UserDTO> users;
    private List<Todo> todos;
    private Integer numberOfPeople;
    private Integer ranking;
}
