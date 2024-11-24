package com.example.umc7.domain.membermission.dto.request;

import com.example.umc7.global.validation.annotation.ValidChallengingMission;

@ValidChallengingMission
public record CreateMemberMissionDTO(
    Long memberId,
    Long missionId
) {

}