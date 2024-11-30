package com.example.umc7.domain.review.converter;

import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.review.Review;
import com.example.umc7.domain.review.dto.request.CreateReviewDTO;
import com.example.umc7.domain.review.dto.response.ReviewResponseDTO;
import com.example.umc7.domain.store.Store;
import java.time.format.DateTimeFormatter;

public class ReviewConverter {

    public static Review toEntity(Member member, Store store, CreateReviewDTO createReviewDTO) {
        return Review.builder()
            .member(member)
            .store(store)
            .content(createReviewDTO.content())
            .score(createReviewDTO.score())
            .build();
    }

    public static ReviewResponseDTO toReviewResponseDTO(Review review) {
        return ReviewResponseDTO.builder()
            .reviewId(review.getId())
            .memberId(review.getMember().getId())
            .name(review.getMember().getName())
            .content(review.getContent())
            .score(review.getScore())
            .datetime(review.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
            .build();
    }
}
