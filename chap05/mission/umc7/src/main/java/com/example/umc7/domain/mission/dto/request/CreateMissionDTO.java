package com.example.umc7.domain.mission.dto.request;

import java.time.LocalDateTime;

public record CreateMissionDTO(
    Long storeId,
    Integer reward,
    LocalDateTime deadLine,
    String missionSpec
) {

}