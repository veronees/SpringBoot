package com.example.umc7.domain.membermission.service;

import com.example.umc7.apipayload.code.status.ErrorStatus;
import com.example.umc7.apipayload.exception.GeneralException;
import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.member.repository.MemberRepository;
import com.example.umc7.domain.membermission.MemberMission;
import com.example.umc7.domain.membermission.converter.MemberMissionConverter;
import com.example.umc7.domain.membermission.dto.request.CreateMemberMissionDTO;
import com.example.umc7.domain.membermission.repository.MemberMissionRepository;
import com.example.umc7.domain.mission.Mission;
import com.example.umc7.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberMissionCommandService {

    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;

    public void saveMemberMission(CreateMemberMissionDTO createMemberMissionDTO) {
        Member member = memberRepository.findById(createMemberMissionDTO.memberId()).orElseThrow(
            () -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND)
        );

        Mission mission = missionRepository.findById(createMemberMissionDTO.missionId())
            .orElseThrow(
                () -> new GeneralException(ErrorStatus.MISSION_NOT_FOUND)
            );

        MemberMission memberMission = MemberMissionConverter.toEntity(member, mission);

        memberMissionRepository.save(memberMission);
    }
}