package com.example.umc7.domain.member.repository.querydsl;

import com.example.umc7.domain.member.Member;

public interface MemberQueryRepository {

    Member findMemberById(Long memberId);
}