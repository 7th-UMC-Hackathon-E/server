package com.umc.hackathon.review.dto;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long userId,
        String content,
        Double progress,
        Integer totalTodos,
        Integer completedTodos,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}