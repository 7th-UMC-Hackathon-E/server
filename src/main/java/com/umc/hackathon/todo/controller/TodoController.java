package com.umc.hackathon.todo.controller;

import com.umc.hackathon.global.apiPayload.ApiResponse;
import com.umc.hackathon.todo.dto.TodoRequest;
import com.umc.hackathon.todo.dto.TodoResponse;
import com.umc.hackathon.todo.exception.TodoNotFoundException;
import com.umc.hackathon.todo.exception.TodoValidationException;
import com.umc.hackathon.todo.service.TodoCommandService;
import com.umc.hackathon.todo.service.TodoQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoCommandService todoCommandService;
    private final TodoQueryService todoQueryService;

    public TodoController(TodoCommandService todoCommandService, TodoQueryService todoQueryService) {
        this.todoCommandService = todoCommandService;
        this.todoQueryService = todoQueryService;
    }

    @Operation(summary = "투두 등록", description = "새로운 Todo를 생성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "투두 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    @PostMapping
    public ApiResponse<?> createTodo(@RequestBody TodoRequest todoRequest) {
        try {
            TodoResponse todoResponse = todoCommandService.createTodo(todoRequest);
            return ApiResponse.onSuccess(todoResponse); // 성공 응답 반환
        } catch (TodoValidationException ex) {
            return ApiResponse.onFailure("TODO_VALIDATION_FAILED", ex.getMessage(), null); // 실패 응답 반환
        }
    }

    @Operation(summary = "투두 단일 조회", description = "단일 Todo를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "투두 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "잘못된 요청 데이터")
    })
    @GetMapping("/{todoId}")
    public ResponseEntity<ApiResponse<TodoResponse>> getTodoById(@PathVariable Long todoId) {
        try {
            TodoResponse todoResponse = todoQueryService.getTodoById(todoId);
            return ResponseEntity.ok(ApiResponse.onSuccess(todoResponse));
        } catch (TodoNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.onFailure("TODO_NOT_FOUND", ex.getMessage(), null));
        }
    }
}
