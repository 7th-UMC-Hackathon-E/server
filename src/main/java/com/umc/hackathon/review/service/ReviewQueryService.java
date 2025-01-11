package com.umc.hackathon.review.service;

import com.umc.hackathon.review.entity.Review;
import com.umc.hackathon.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;

    public ReviewQueryService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("해당 회고록을 찾을 수 없습니다."));
    }
}
