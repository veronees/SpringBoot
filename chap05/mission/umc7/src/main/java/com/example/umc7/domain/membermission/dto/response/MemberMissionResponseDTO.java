package com.example.umc7.domain.membermission.dto.response;

import lombok.Builder;

@Builder
public record MemberMissionResponseDTO(
    Long memberMissionId,
    Long storeId,
    Long missionId,
    Integer reward,
    String deadLine,
    String missionSpec,
    boolean clearStatus
) {

}