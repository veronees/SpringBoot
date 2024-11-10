package com.example.umc7.domain.mission.repository.querydsl;

import com.example.umc7.domain.mission.Mission;
import com.example.umc7.domain.region.Region;
import java.util.List;

public interface MissionQueryRepository {
    List<Mission> findMissionByRegion(String region, int offset, int limit);
}