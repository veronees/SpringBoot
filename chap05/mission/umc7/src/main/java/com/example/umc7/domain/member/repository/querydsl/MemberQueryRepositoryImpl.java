package com.example.umc7.domain.member.repository.querydsl;

import static com.example.umc7.domain.member.QMember.*;

import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.member.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Member findMemberById(Long memberId) {
        return jpaQueryFactory.selectFrom(member)
            .where(member.id.eq(memberId))
            .fetchOne();
    }
}