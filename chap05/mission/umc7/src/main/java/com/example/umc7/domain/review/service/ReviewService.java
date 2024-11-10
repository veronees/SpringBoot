package com.example.umc7.domain.review.service;

import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.review.repository.ReviewRepository;
import com.example.umc7.domain.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public void saveReview(Member member, Store store, String content, Float score) {
        reviewRepository.saveReview(member, store, content, score);
    }
}
