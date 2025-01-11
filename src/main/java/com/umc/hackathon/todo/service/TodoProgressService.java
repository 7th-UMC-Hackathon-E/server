package com.umc.hackathon.todo.service;


import com.umc.hackathon.todo.dto.TodoProgressResponse;
import com.umc.hackathon.todo.entity.Todo;
import com.umc.hackathon.todo.repository.TodoRepository;
import com.umc.hackathon.todo.exception.TodoNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TodoProgressService {

    private final TodoRepository todoRepository;

    public TodoProgressService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoProgressResponse getTodoProgress(Long memberId, LocalDate date) {
        // 해당 멤버의 투두 목록 조회
        List<Todo> todos = todoRepository.findByMemberIdAndCreatedAtDate(memberId, date);

        // 투두가 없으면 예외 처리
        if (todos.isEmpty()) {
            throw new TodoNotFoundException("해당 멤버의 투두를 찾을 수 없습니다.");
        }

        // 전체 투두 수와 완료된 투두 수 계산
        int totalTodos = todos.size();
        int completedTodos = (int) todos.stream().filter(Todo::isStatus).count();

        // 진행도 계산
        int progressPercentage = (int) ((completedTodos * 100) / totalTodos);

        return new TodoProgressResponse(totalTodos, completedTodos, progressPercentage);
    }
}