package com.umc.hackathon.todo.dto;

public record TodoProgressRequest(
        Long memberId,
        String date
) { }
