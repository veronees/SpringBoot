package com.example.umc7.domain.store.service;

import com.example.umc7.apipayload.code.status.ErrorStatus;
import com.example.umc7.apipayload.exception.GeneralException;
import com.example.umc7.domain.region.Region;
import com.example.umc7.domain.region.repository.RegionRepository;
import com.example.umc7.domain.store.dto.request.CreateStoreDTO;
import com.example.umc7.domain.store.Store;
import com.example.umc7.domain.store.converter.StoreConverter;
import com.example.umc7.domain.store.dto.response.StoreIdResponseDTO;
import com.example.umc7.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreCommandService {

    private final StoreRepository storeRepository;
    private final RegionRepository regionRepository;

    public StoreIdResponseDTO saveStore(CreateStoreDTO createStoreDTO) {
        Region region = regionRepository.findByName(createStoreDTO.region()).orElseThrow(
            () -> new GeneralException(ErrorStatus.REGION_NOT_FOUND)
        );

        Store store = StoreConverter.toEntity(region, createStoreDTO);

        storeRepository.save(store);

        return StoreConverter.toStoreIdResponseDTO(store);
    }
}