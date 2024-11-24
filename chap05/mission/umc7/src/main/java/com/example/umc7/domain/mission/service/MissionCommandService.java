package com.example.umc7.domain.mission.service;

import com.example.umc7.apipayload.code.status.ErrorStatus;
import com.example.umc7.apipayload.exception.GeneralException;
import com.example.umc7.domain.mission.Mission;
import com.example.umc7.domain.mission.converter.MissionConverter;
import com.example.umc7.domain.mission.dto.request.CreateMissionDTO;
import com.example.umc7.domain.mission.repository.MissionRepository;
import com.example.umc7.domain.store.Store;
import com.example.umc7.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionCommandService {

    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;

    public void saveMission(CreateMissionDTO createMissionDTO) {
        Store store = storeRepository.findById(createMissionDTO.storeId()).orElseThrow(
            () -> new GeneralException(ErrorStatus.STORE_NOT_FOUND)
        );

        Mission mission = MissionConverter.toEntity(store, createMissionDTO);

        missionRepository.save(mission);
    }
}