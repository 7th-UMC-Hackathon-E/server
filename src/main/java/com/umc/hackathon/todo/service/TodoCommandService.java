package com.umc.hackathon.todo.service;

import com.umc.hackathon.todo.dto.TodoRequest;
import com.umc.hackathon.todo.dto.TodoResponse;
import com.umc.hackathon.todo.entity.Todo;
import com.umc.hackathon.todo.exception.TodoNotFoundException;
import com.umc.hackathon.todo.exception.TodoValidationException;
import com.umc.hackathon.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoCommandService {

    private final TodoRepository todoRepository;

    public TodoCommandService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoResponse createTodo(TodoRequest todoRequest) {
        if (todoRequest.description() == null || todoRequest.description().trim().isEmpty()) {
            throw new TodoValidationException("투두 내용을 입력해야 합니다.");
        }

        Todo todo = new Todo();
        todo.setMemberId(todoRequest.memberId());
        todo.setDescription(todoRequest.description());
        todo.setStatus(false); // 기본 상태는 false

        Todo savedTodo = todoRepository.save(todo);

        return new TodoResponse(savedTodo.getId(), savedTodo.getMemberId(), savedTodo.getDescription(),
                savedTodo.isStatus(), savedTodo.getCreatedAt(), savedTodo.getUpdatedAt());
    }

    @Transactional
    public void deleteTodoById(Long id) {
        // 삭제할 Todo가 있는지 확인
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException("해당 투두를 찾을 수 없습니다.");
        }

        // Todo 삭제
        todoRepository.deleteById(id);
    }
}
