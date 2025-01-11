package com.umc.hackathon.review.controller;

import com.umc.hackathon.global.apiPayload.ApiResponse;
import com.umc.hackathon.review.dto.ReviewRequest;
import com.umc.hackathon.review.dto.ReviewResponse;
import com.umc.hackathon.review.service.ReviewCommandService;
import com.umc.hackathon.room.exception.UserStudyNotFoundException;
import com.umc.hackathon.user.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewCommandService reviewCommandService;

    public ReviewController(ReviewCommandService reviewCommandService) {
        this.reviewCommandService = reviewCommandService;
    }

    @Operation(summary = "회고록 등록", description = "회고록을 작성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회고록 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "회고록 내용 조회 실패"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "멤버 조회 실패")
    })
    @PostMapping
    public ApiResponse<?> createReview(@RequestBody ReviewRequest request) {
        try {
            ReviewResponse reviewResponse = reviewCommandService.createReview(request);
            return ApiResponse.onSuccess(reviewResponse);
        } catch (UserNotFoundException ex) {
            return ApiResponse.onFailure("USER_NOT_FOUND", ex.getMessage(), null);
        } catch (UserStudyNotFoundException ex) {
            return ApiResponse.onFailure("USER_STUDY_NOT_FOUND", ex.getMessage(), null);
        } catch (Exception ex) {
            return ApiResponse.onFailure("COMMON500", "서버 에러, 관리자에게 문의 바랍니다.", null);
        }
    }

}