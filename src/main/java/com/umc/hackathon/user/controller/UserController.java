package com.umc.hackathon.user.controller;

import com.umc.hackathon.global.apiPayload.ApiResponse;
import com.umc.hackathon.user.dto.UserRequestDTO;
import com.umc.hackathon.user.dto.UserResponseDTO;
import com.umc.hackathon.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "회원가입", description = "새로운 유저 회원가입")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    @PostMapping("/regist")
    public ApiResponse<?> register(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            userService.RegisterUser(userRequestDTO); // 사용자 등록 로직 실행
            return ApiResponse.onSuccess(); // 성공 응답 반환
        } catch (IllegalArgumentException ex) {
            return ApiResponse.onFailure("REGIST_FAILED", ex.getMessage(), null); // 실패 응답 반환
        }
    }
    @Operation(summary = "로그인", description = "유저 로그인")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    @GetMapping("/login")
    public ApiResponse<UserResponseDTO> login(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO res = userService.GetUserData();
            return ApiResponse.onSuccess(res);
        } catch (IllegalArgumentException ex) {
            return ApiResponse.onFailure("LOGIN_FAILED", ex.getMessage(), null);
        }
    }
}
