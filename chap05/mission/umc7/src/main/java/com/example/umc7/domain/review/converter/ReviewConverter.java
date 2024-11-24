package com.example.umc7.domain.review.converter;

import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.review.Review;
import com.example.umc7.domain.review.dto.request.CreateReviewDTO;
import com.example.umc7.domain.store.Store;

public class ReviewConverter {

    public static Review toEntity(Member member, Store store, CreateReviewDTO createReviewDTO) {
        return Review.builder()
            .member(member)
            .store(store)
            .content(createReviewDTO.content())
            .score(createReviewDTO.score())
            .build();
    }
}
