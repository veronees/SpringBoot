package com.example.umc7.domain.review.dto.request;

import com.example.umc7.global.validation.annotation.ValidExistStore;

public record CreateReviewDTO(

    @ValidExistStore
    Long storeId,
    Long memberId, // 일단 이렇게 받음
    String content,
    float score
) {

}