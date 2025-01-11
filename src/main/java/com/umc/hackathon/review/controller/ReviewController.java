package com.umc.hackathon.review.controller;

import com.umc.hackathon.global.apiPayload.ApiResponse;
import com.umc.hackathon.review.dto.ReviewRequest;
import com.umc.hackathon.review.dto.ReviewResponse;
import com.umc.hackathon.review.entity.Review;
import com.umc.hackathon.review.exception.ReviewNotFoundException;
import com.umc.hackathon.review.service.ReviewCommandService;
import com.umc.hackathon.review.service.ReviewQueryService;
import com.umc.hackathon.room.exception.UserStudyNotFoundException;
import com.umc.hackathon.todo.exception.TodoNotFoundException;
import com.umc.hackathon.user.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    public ReviewController(ReviewCommandService reviewCommandService, ReviewQueryService reviewQueryService) {
        this.reviewCommandService = reviewCommandService;
        this.reviewQueryService = reviewQueryService;
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

    @Operation(summary = "회고록 삭제", description = "회고록을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회고록 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회고록 조회 실패")
    })
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long reviewId) {
        try {
            reviewCommandService.deleteReview(reviewId);
            return ResponseEntity.ok(ApiResponse.onSuccess(null));
        } catch (TodoNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.onFailure("REVIEW_NOT_FOUND", ex.getMessage(), null));
        }
    }

    @Operation(summary = "회고록 조회", description = "회고록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회고록 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회고록 조회 실패")
    })
    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReview(@PathVariable Long reviewId) {
        try {
            // 회고록 조회
            Review review = reviewQueryService.getReviewById(reviewId);

            // 응답 형식에 맞게 포맷
            ReviewResponse response = new ReviewResponse(
                    review.getId(),
                    review.getUser().getUserId(),  // memberId
                    review.getContent(),
                    review.getProgress(),
                    review.getNumberAll(),
                    review.getNumberDone(),
                    review.getCreatedAt(),
                    review.getUpdatedAt()
            );

            return ResponseEntity.ok(ApiResponse.onSuccess(response));

        } catch (ReviewNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.onFailure("REVIEW_NOT_FOUND", ex.getMessage(), null));
        }
    }
}