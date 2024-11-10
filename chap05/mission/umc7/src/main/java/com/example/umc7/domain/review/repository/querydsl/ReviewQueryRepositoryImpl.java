package com.example.umc7.domain.review.repository.querydsl;

import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.review.Review;
import com.example.umc7.domain.store.Store;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository {

    private final EntityManager entityManager;

    @Override
    public void saveReview(Member member, Store store, String content, Float score) {
        Review review = Review.builder()
            .member(member)
            .store(store)
            .content(content)
            .score(score)
            .build();

        entityManager.persist(review);
    }
}