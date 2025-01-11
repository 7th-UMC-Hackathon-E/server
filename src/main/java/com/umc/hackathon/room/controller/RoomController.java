package com.umc.hackathon.room.controller;

import com.umc.hackathon.global.apiPayload.ApiResponse;
import com.umc.hackathon.room.dto.EnterRoomDTO;
import com.umc.hackathon.room.dto.RoomDTO;
import com.umc.hackathon.room.dto.RoomListDTO;
import com.umc.hackathon.room.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Operation(summary = "방 리스트 조회", description = "전체 방 리스트를 조회")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    @GetMapping("/list")
    public ApiResponse<RoomListDTO> getRooms() {
        try{
            RoomListDTO roomListDTO = roomService.getRoomList();
            return ApiResponse.onSuccess(roomListDTO);
        }
        catch(IllegalArgumentException e){
            return ApiResponse.onFailure("GET_FAILED", e.getMessage(), null);
        }
    }
    @Operation(summary = "방 입장", description = "방에 입장합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "입장 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    @PostMapping("/{roomId}/enter")
    public ApiResponse<?> enterRoom(@PathVariable Long roomId) {
        try {
            EnterRoomDTO enterRoomDTO = new EnterRoomDTO(1L,roomId);
            roomService.enterRoom(enterRoomDTO);
            return ApiResponse.onSuccess();
        } catch (IllegalArgumentException ex) {
            return ApiResponse.onFailure("Enter_FAILED", ex.getMessage(), null);
        }
    }

    @Operation(summary = "방 정보 상세 조회", description = "50분마다 호출")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    @GetMapping("/{roomId}/detail")
    public ApiResponse<RoomDTO> getRoom(@PathVariable Long roomId) {
        try{
            RoomDTO roomDTO = new RoomDTO();
            roomDTO = roomService.getRoomDetail(1L,roomId);
            return ApiResponse.onSuccess(roomDTO);
        } catch (IllegalArgumentException ex) {
            return ApiResponse.onFailure("GET_FAILED", ex.getMessage(), null);
        }
    }

    @Operation(summary = "방 떠나기", description = "회고록 작성 직후 종료 누를 시 호출")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "떠나기 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    @DeleteMapping("/{roomId}/leave")
    public ApiResponse<?> leaveRoom(@PathVariable Long roomId) {
        try{
            roomService.leaveRoom(roomId);
            return ApiResponse.onSuccess();
        }
        catch(IllegalArgumentException e){
            return ApiResponse.onFailure("Leave_FAILED", e.getMessage(), null);
        }
    }
}
