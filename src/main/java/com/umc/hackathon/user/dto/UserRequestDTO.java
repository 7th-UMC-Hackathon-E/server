package com.umc.hackathon.user.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserRequestDTO {
    private String name;
    private LocalDate birth;
}
