package com.example.umc7.domain.review.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.member.enums.Gender;
import com.example.umc7.domain.member.enums.SocialType;
import com.example.umc7.domain.store.Store;
import com.example.umc7.domain.store.repository.StoreRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    StoreRepository storeRepository;

    @Test
    @DisplayName("리뷰를 저장한다")
    void test1() {
        Member member = Member.builder()
            .name("베로")
            .gender(Gender.MAN)
            .age(25)
            .address("역곡 어딘가")
            .socialType(SocialType.NAVER)
            .email("abc123@naver.com")
            .point(0)
            .build();

        entityManager.persist(member);

        Store store = storeRepository.findById(1L).get();

        reviewService.saveReview(member, store, "맛있어요", 5.0f);
    }
}