package com.umc.hackathon.todo.controller;

import com.umc.hackathon.global.apiPayload.ApiResponse;
import com.umc.hackathon.todo.dto.TodoRequest;
import com.umc.hackathon.todo.dto.TodoResponse;
import com.umc.hackathon.todo.exception.TodoNotFoundException;
import com.umc.hackathon.todo.exception.TodoValidationException;
import com.umc.hackathon.todo.service.TodoCommandService;
import com.umc.hackathon.todo.service.TodoQueryService;
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

    @PostMapping
    public ResponseEntity<ApiResponse<TodoResponse>> createTodo(@RequestBody TodoRequest todoRequest) {
        try {
            TodoResponse todoResponse = todoCommandService.createTodo(todoRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "2000", "OK", todoResponse));
        } catch (TodoValidationException ex) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "4001", ex.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TodoResponse>> getTodoById(@PathVariable Long id) {
        try {
            TodoResponse todoResponse = todoQueryService.getTodoById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "2000", "OK", todoResponse));
        } catch (TodoNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "4002", ex.getMessage(), null));
        }
    }
}
