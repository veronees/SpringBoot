package com.example.umc7.domain.review.service;

import com.example.umc7.apipayload.code.status.ErrorStatus;
import com.example.umc7.apipayload.exception.GeneralException;
import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.member.repository.MemberRepository;
import com.example.umc7.domain.review.Review;
import com.example.umc7.domain.review.converter.ReviewConverter;
import com.example.umc7.domain.review.dto.request.CreateReviewDTO;
import com.example.umc7.domain.review.repository.ReviewRepository;
import com.example.umc7.domain.store.Store;
import com.example.umc7.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public void saveReview(CreateReviewDTO createReviewDTO) {
        Member member = memberRepository.findById(createReviewDTO.memberId()).orElseThrow(
            () -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND)
        );

        Store store = storeRepository.findById(createReviewDTO.storeId()).orElseThrow(
            () -> new GeneralException(ErrorStatus.STORE_NOT_FOUND)
        );

        Review review = ReviewConverter.toEntity(member, store, createReviewDTO);

        reviewRepository.save(review);
    }
}