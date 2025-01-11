package com.umc.hackathon.todo.exception;

public class InvalidDateFormatException extends RuntimeException {

    // 메시지를 전달받는 생성자
    public InvalidDateFormatException(String message) {
        super(message);
    }
}
