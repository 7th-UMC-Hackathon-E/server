package com.umc.hackathon.review.service;

import com.umc.hackathon.review.dto.ReviewRequest;
import com.umc.hackathon.review.dto.ReviewResponse;
import com.umc.hackathon.review.entity.Review;
import com.umc.hackathon.room.exception.UserStudyNotFoundException;
import com.umc.hackathon.user.exception.UserNotFoundException;
import com.umc.hackathon.review.repository.ReviewRepository;
import com.umc.hackathon.room.entity.UserStudy;
import com.umc.hackathon.room.repository.UserStudyRepository;
import com.umc.hackathon.user.entity.User;
import com.umc.hackathon.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewCommandService {
    private final ReviewRepository reviewRepository;
    private final UserStudyRepository userStudyRepository;
    private final UserRepository userRepository;

    public ReviewCommandService(ReviewRepository reviewRepository, UserStudyRepository userStudyRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userStudyRepository = userStudyRepository;
        this.userRepository = userRepository;
    }

    public ReviewResponse createReview(ReviewRequest request) {

        if (request.content() == null || request.content().isBlank()) {
            throw new IllegalArgumentException("회고록 내용을 입력해야 합니다.");
        }

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException("해당 멤버를 찾을 수 없습니다."));
        System.out.println("user.getUserId() = " + user.getUserId());
        UserStudy userStudy = userStudyRepository.findByUser(user)
                .orElseThrow(() -> new UserStudyNotFoundException("해당 멤버의 스터디 정보를 찾을 수 없습니다."));

        Review review = new Review(
                user,
                request.content(),
                userStudy.getProgress(),
                userStudy.getNumberAll(),
                userStudy.getNumberDone()
        );

        reviewRepository.save(review);


        System.out.println("Progress = " + userStudy.getProgress());

        return new ReviewResponse(
                review.getId(),
                user.getUserId(),
                review.getContent(),
                review.getProgress(),
                review.getNumberAll(),
                review.getNumberDone(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("해당 리뷰가 없습니다. -> " + reviewId));

        reviewRepository.delete(review);
    }
}
