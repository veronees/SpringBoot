package com.example.umc7.domain.review.repository;

import com.example.umc7.domain.review.Review;
import com.example.umc7.domain.review.repository.querydsl.ReviewQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewQueryRepository {

}