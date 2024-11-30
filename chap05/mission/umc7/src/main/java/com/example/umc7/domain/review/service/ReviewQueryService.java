package com.example.umc7.domain.review.service;

import com.example.umc7.apipayload.PageResponseDTO;
import com.example.umc7.domain.review.Review;
import com.example.umc7.domain.review.converter.ReviewConverter;
import com.example.umc7.domain.review.dto.response.ReviewResponseDTO;
import com.example.umc7.domain.review.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;

    public PageResponseDTO<List<ReviewResponseDTO>> getReviewList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Review> reviewPage = reviewRepository.findAll(pageable);

        List<ReviewResponseDTO> reviewResponseDTOList = reviewPage.stream()
            .map(review -> ReviewConverter.toReviewResponseDTO(review))
            .toList();

        return PageResponseDTO.<List<ReviewResponseDTO>>builder()
            .page(page)
            .hasNext(reviewPage.hasNext())
            .result(reviewResponseDTOList)
            .build();
    }
}