package com.umc.hackathon.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest(
        @NotNull Long userId,
        @NotBlank String content  // ✅ content가 빈 문자열이거나 null이면 요청 실패
//        @NotNull Double progress,
//        @NotNull Integer numberAll,
//        @NotNull Integer numberDone
) {}