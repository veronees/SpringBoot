package com.example.umc7.domain.store.dto.response;

import lombok.Builder;

@Builder
public record StoreMissionResponseDto(
    Long storeId,
    Long missionId,
    Integer reward,
    String deadLine,
    String missionSpec
) {

}
