package com.example.umc7.domain.membermission.converter;

import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.membermission.MemberMission;
import com.example.umc7.domain.membermission.dto.response.MemberMissionResponseDTO;
import com.example.umc7.domain.mission.Mission;
import java.time.format.DateTimeFormatter;

public class MemberMissionConverter {

    public static MemberMission toEntity(Member member, Mission mission) {
        return MemberMission.builder()
            .member(member)
            .mission(mission)
            .clearStatus(false)
            .build();
    }

    public static MemberMissionResponseDTO toMemberMissionResponseDTO(MemberMission memberMission) {
        return MemberMissionResponseDTO.builder()
            .memberMissionId(memberMission.getId())
            .storeId(memberMission.getMission().getStore().getId())
            .missionId(memberMission.getMission().getId())
            .reward(memberMission.getMission().getReward())
            .deadLine(memberMission.getMission().getDeadLine()
                .format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
            .missionSpec(memberMission.getMission().getMissionSpec())
            .clearStatus(memberMission.isClearStatus())
            .build();
    }
}