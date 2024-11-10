package com.example.umc7.domain.review.repository.querydsl;

import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.store.Store;

public interface ReviewQueryRepository {

    void saveReview(Member member, Store store, String content, Float score);
}
