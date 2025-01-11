package com.umc.hackathon.todo.service;

import com.umc.hackathon.todo.dto.TodoResponse;
import com.umc.hackathon.todo.entity.Todo;
import com.umc.hackathon.todo.exception.TodoNotFoundException;
import com.umc.hackathon.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoQueryService {

    private final TodoRepository todoRepository;

    public TodoQueryService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoResponse getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("투두를 찾을 수 없습니다."));

        return new TodoResponse(todo.getId(), todo.getMemberId(), todo.getDescription(),
                todo.isStatus(), todo.getCreatedAt(), todo.getUpdatedAt());
    }
}
