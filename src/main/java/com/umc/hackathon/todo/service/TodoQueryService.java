package com.umc.hackathon.todo.service;

import com.umc.hackathon.todo.dto.TodoListResponse;
import com.umc.hackathon.todo.dto.TodoResponse;
import com.umc.hackathon.todo.entity.Todo;
import com.umc.hackathon.todo.exception.InvalidDateFormatException;
import com.umc.hackathon.todo.exception.TodoNotFoundException;
import com.umc.hackathon.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<TodoListResponse> getTodosByDateAndMemberId(Long memberId, String date) {
        // 날짜 형식 체크 (yyyy-MM-dd)
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("날짜 형식 오류");
        }

        // memberId와 date에 맞는 투두 조회
        List<Todo> todos = todoRepository.findByMemberIdAndCreatedAtDate(memberId, localDate);

        if (todos.isEmpty()) {
            throw new TodoNotFoundException("해당 날짜에 대한 투두가 없습니다.");
        }

        // Todo를 DTO로 변환
        List<TodoResponse> todoResponses = todos.stream()
                .map(todo -> new TodoResponse(
                        todo.getId(),
                        todo.getMemberId(),
                        todo.getDescription(),
                        todo.isStatus(),
                        todo.getCreatedAt(),
                        todo.getUpdatedAt()
                ))
                .collect(Collectors.toList());


        // 날짜별 투두 목록을 만든다.
        // 여러 날짜가 포함될 수 있으므로 List<TodoListResponse>를 반환
        return List.of(new TodoListResponse(
                memberId,
                localDate,
                todoResponses
        ));
    }
}
