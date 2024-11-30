package com.example.umc7.domain.review.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ReviewResponseDTO(
    Long reviewId,
    Long memberId,
    String name,
    String content,
    float score,
    String datetime

) {

}