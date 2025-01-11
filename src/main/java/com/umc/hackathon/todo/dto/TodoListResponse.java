package com.umc.hackathon.todo.dto;

import java.util.List;

public record TodoListResponse(
        Long memberId,
        String date,
        List<TodoResponse> todos
) {
}