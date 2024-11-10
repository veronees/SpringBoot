package com.example.umc7.domain.membermission.repository.querydsl;

import static com.example.umc7.domain.membermission.QMemberMission.*;

import com.example.umc7.domain.membermission.MemberMission;
import com.example.umc7.domain.membermission.QMemberMission;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberMissionQueryRepositoryImpl implements MemberMissionQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MemberMission> findMemberMissionByClearStatus(boolean clearStatus, int offset,
        int limit) {
        return jpaQueryFactory.selectFrom(memberMission)
            .join(memberMission.mission).fetchJoin()
            .join(memberMission.mission.store).fetchJoin()
            .where(memberMission.clearStatus.eq(clearStatus))
            .offset(offset)
            .limit(limit)
            .fetch();
    }
}