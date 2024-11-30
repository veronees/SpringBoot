package com.example.umc7.domain.store.service;

import com.example.umc7.apipayload.PageResponseDTO;
import com.example.umc7.apipayload.code.status.ErrorStatus;
import com.example.umc7.apipayload.exception.GeneralException;
import com.example.umc7.domain.mission.Mission;
import com.example.umc7.domain.mission.repository.MissionRepository;
import com.example.umc7.domain.store.Store;
import com.example.umc7.domain.store.converter.StoreConverter;
import com.example.umc7.domain.store.dto.response.StoreMissionResponseDto;
import com.example.umc7.domain.store.repository.StoreRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreQueryService {

    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;

    public Optional<Store> findStore(Long id) {
        return storeRepository.findById(id);
    }

    public List<Store> findStoresByNameAndScore(String name, Float score) {
        List<Store> filteredScores = storeRepository.dynamicQueryWithBooleanBuilder(name, score);

        filteredScores.forEach(store -> log.info("Store: {}", store.getName()));

        return filteredScores;
    }

    public PageResponseDTO<List<StoreMissionResponseDto>> getStoreMissionList(Long storeId,
        int page, int size) {
        Store store = storeRepository.findById(storeId).orElseThrow(
            () -> new GeneralException(ErrorStatus.STORE_NOT_FOUND)
        );

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Mission> missionPage = missionRepository.findByStore(store, pageable);

        List<StoreMissionResponseDto> storeMissionResponseDtoList = missionPage.stream()
            .map(mission -> StoreConverter.toStoreMissionResponseDto(mission))
            .toList();

        return PageResponseDTO.<List<StoreMissionResponseDto>>builder()
            .page(page)
            .hasNext(missionPage.hasNext())
            .result(storeMissionResponseDtoList)
            .build();
    }
}