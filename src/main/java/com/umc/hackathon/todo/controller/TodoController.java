package com.umc.hackathon.todo.controller;

import com.umc.hackathon.global.apiPayload.ApiResponse;
import com.umc.hackathon.todo.dto.*;
import com.umc.hackathon.todo.exception.TodoNotFoundException;
import com.umc.hackathon.todo.exception.TodoValidationException;
import com.umc.hackathon.todo.service.TodoCommandService;
import com.umc.hackathon.todo.service.TodoProgressService;
import com.umc.hackathon.todo.service.TodoQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoCommandService todoCommandService;
    private final TodoQueryService todoQueryService;
    private final TodoProgressService todoProgressService;

    public TodoController(TodoCommandService todoCommandService, TodoQueryService todoQueryService, TodoProgressService todoProgressService) {
        this.todoCommandService = todoCommandService;
        this.todoQueryService = todoQueryService;
        this.todoProgressService = todoProgressService;
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

    @Operation(summary = "투두 목록 조회", description = "Todo 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "투두 등록 성공")
    })
    @GetMapping("/todos")
    public ResponseEntity<ApiResponse<List<TodoListResponse>>> getTodosByDateAndMemberId(
            @RequestParam Long memberId,
            @RequestParam String date) {
            // 서비스 레벨에서 투두 목록을 조회
            List<TodoListResponse> todoListResponse = todoQueryService.getTodosByDateAndMemberId(memberId, date);


//        // 투두가 없으면, 지정된 형식으로 빈 리스트 반환
//        if (todoListResponse.isEmpty()) {
//            TodoListResponse emptyTodoResponse = new TodoListResponse(memberId, date, new ArrayList<>());
//            List<TodoListResponse> result = Collections.singletonList(emptyTodoResponse);
//
//            ApiResponse<List<TodoListResponse>> response = new ApiResponse<>(
//                    true, "4230", "OK", result
//            );
//            return ResponseEntity.ok(response);
//        }


            return ResponseEntity.ok(ApiResponse.onSuccess(todoListResponse));
    }

    @Operation(summary = "투두 단일 삭제", description = "특정 ID를 가진 Todo를 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "투두 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "투두를 찾을 수 없음")
    })
    @DeleteMapping("/{todoId}")
    public ResponseEntity<ApiResponse<Void>> deleteTodoById(@PathVariable Long todoId) {
        try {
            todoCommandService.deleteTodoById(todoId);
            return ResponseEntity.ok(ApiResponse.onSuccess(null));
        } catch (TodoNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.onFailure("TODO_NOT_FOUND", ex.getMessage(), null));
        }
    }

    @Operation(summary = "투두 진행도 조회", description = "투두의 진행도를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "투두 진행도 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "투두를 찾을 수 없음")
    })
    @GetMapping("/progress")
    public ResponseEntity<ApiResponse<TodoProgressResponse>> getTodoProgress(
            @RequestParam Long memberId,
            @RequestParam String date) {
        try {
            TodoProgressResponse progressResponse = todoProgressService.getTodoProgress(memberId, LocalDate.parse(date));
            return ResponseEntity.ok(ApiResponse.onSuccess(progressResponse));
        } catch (TodoNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.onFailure("TODO_NOT_FOUND", ex.getMessage(), null));
        }
    }

    @Operation(summary = "투두 상태 변경", description = "투두 항목의 상태를 완료로 변경합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "투두 상태 변경 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "투두 항목을 찾을 수 없음")
    })
    @PatchMapping("/{todoId}/status")
    public ResponseEntity<ApiResponse<TodoResponse>> updateTodoStatus(
            @PathVariable Long todoId,
            @RequestBody TodoStatusUpdateRequest request) {
        try {
            TodoResponse todoResponse = todoCommandService.updateTodoStatus(todoId, request.status());
            return ResponseEntity.ok(ApiResponse.onSuccess(todoResponse));
        } catch (TodoNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.onFailure("TODO_NOT_FOUND", ex.getMessage(), null));
        }
    }
}
