package com.umc.hackathon.todo.dto;

public record TodoRequest(
        Long memberId,
        String description
) {}
