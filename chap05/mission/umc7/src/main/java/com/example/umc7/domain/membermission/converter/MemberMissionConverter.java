package com.example.umc7.domain.membermission.converter;

import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.membermission.MemberMission;
import com.example.umc7.domain.mission.Mission;

public class MemberMissionConverter {

    public static MemberMission toEntity(Member member, Mission mission) {
        return MemberMission.builder()
            .member(member)
            .mission(mission)
            .clearStatus(false)
            .build();
    }
}