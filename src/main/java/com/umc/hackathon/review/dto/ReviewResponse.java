package com.umc.hackathon.review.dto;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long userId,
        String content,
        Double progress,
        Integer numberAll,
        Integer numberDone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}