package com.example.umc7.domain.member.repository;

import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.member.repository.querydsl.MemberQueryRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {

    Optional<Member> findByClientId(String clientId);
}