package com.example.umc7.domain.mission.converter;

import com.example.umc7.domain.mission.Mission;
import com.example.umc7.domain.mission.dto.request.CreateMissionDTO;
import com.example.umc7.domain.store.Store;

public class MissionConverter {

    public static Mission toEntity(Store store, CreateMissionDTO createMissionDTO) {
        return Mission.builder()
            .store(store)
            .reward(createMissionDTO.reward())
            .deadLine(createMissionDTO.deadLine())
            .missionSpec(createMissionDTO.missionSpec())
            .build();
    }
}