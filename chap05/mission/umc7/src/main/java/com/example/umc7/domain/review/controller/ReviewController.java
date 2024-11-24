package com.example.umc7.domain.review.controller;

import com.example.umc7.apipayload.ApiResponse;
import com.example.umc7.domain.review.dto.request.CreateReviewDTO;
import com.example.umc7.domain.review.service.ReviewCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewCommandService reviewCommandService;

    public ResponseEntity<ApiResponse<String>> createReview(
        @RequestBody @Valid CreateReviewDTO createReviewDTO) {
        reviewCommandService.saveReview(createReviewDTO);
        return ResponseEntity.ok(ApiResponse.onSuccess("리뷰 작성 완료"));
    }
}