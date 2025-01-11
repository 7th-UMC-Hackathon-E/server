package com.umc.hackathon.user.dto;

import com.umc.hackathon.user.entity.Zodiac;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserResponseDTO {
    private String name;
    private LocalDate birth;
    private Zodiac zodiac;
}
