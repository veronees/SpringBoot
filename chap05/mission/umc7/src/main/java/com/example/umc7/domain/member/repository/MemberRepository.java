package com.example.umc7.domain.member.repository;

import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.member.repository.querydsl.MemberQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {

}