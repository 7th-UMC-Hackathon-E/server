package com.umc.hackathon.todo.dto;

public record TodoProgressResponse(
        int totalTodos,
        int completedTodos,
        int progressPercentage
) { }
