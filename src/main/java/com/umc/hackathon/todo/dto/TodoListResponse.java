package com.umc.hackathon.todo.dto;

import java.time.LocalDate;
import java.util.List;

public record TodoListResponse(
        Long memberId,
        LocalDate date,
        List<TodoResponse> todos
) {
}