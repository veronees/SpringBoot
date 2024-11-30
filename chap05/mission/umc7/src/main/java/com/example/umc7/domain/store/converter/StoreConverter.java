package com.example.umc7.domain.store.converter;

import com.example.umc7.domain.mission.Mission;
import com.example.umc7.domain.region.Region;
import com.example.umc7.domain.store.dto.request.CreateStoreDTO;
import com.example.umc7.domain.store.Store;
import com.example.umc7.domain.store.dto.response.StoreIdResponseDTO;
import com.example.umc7.domain.store.dto.response.StoreMissionResponseDto;
import java.time.format.DateTimeFormatter;

public class StoreConverter {

    public static Store toEntity(Region region, CreateStoreDTO createStoreDTO) {
        return Store.builder()
            .region(region)
            .name(createStoreDTO.name())
            .address(createStoreDTO.address())
            .score(createStoreDTO.score())
            .build();
    }

    public static StoreIdResponseDTO toStoreIdResponseDTO(Store store) {
        return StoreIdResponseDTO.builder()
            .id(store.getId())
            .build();
    }

    public static StoreMissionResponseDto toStoreMissionResponseDto(Mission mission) {
        return StoreMissionResponseDto.builder()
            .storeId(mission.getStore().getId())
            .missionId(mission.getId())
            .reward(mission.getReward())
            .deadLine(mission.getDeadLine().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
            .missionSpec(mission.getMissionSpec())
            .build();
    }
}