package com.umc.hackathon.todo.dto;

import java.time.LocalDateTime;

public record TodoResponse(
        Long id,
        Long memberId,
        String description,
        boolean status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
