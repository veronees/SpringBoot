package com.example.umc7.domain.membermission.repository;

import com.example.umc7.domain.membermission.MemberMission;
import com.example.umc7.domain.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    boolean existsByMemberIdAndMissionIdAndClearStatusIsTrue(Long memberId, Long missionId);
}
