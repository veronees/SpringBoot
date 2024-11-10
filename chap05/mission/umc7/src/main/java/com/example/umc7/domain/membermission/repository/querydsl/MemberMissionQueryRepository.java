package com.example.umc7.domain.membermission.repository.querydsl;

import com.example.umc7.domain.membermission.MemberMission;
import com.example.umc7.domain.mission.Mission;
import java.util.List;

public interface MemberMissionQueryRepository {
    List<MemberMission> findMemberMissionByClearStatus(boolean clearStatus, int offset, int limit);
}