package com.example.umc7.domain.mission.repository.querydsl;

import static com.example.umc7.domain.mission.QMission.*;

import com.example.umc7.domain.mission.Mission;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MissionQueryRepositoryImpl implements MissionQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Mission> findMissionByRegion(String region, int offset, int limit) {
        return jpaQueryFactory.selectFrom(mission)
            .join(mission.store).fetchJoin()
            .where(mission.store.region.name.eq(region).and(mission.deadLine.after(LocalDateTime.now())))
            .fetch();
    }
}