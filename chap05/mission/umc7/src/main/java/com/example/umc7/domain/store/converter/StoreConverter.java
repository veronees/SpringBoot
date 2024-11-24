package com.example.umc7.domain.store.converter;

import com.example.umc7.domain.region.Region;
import com.example.umc7.domain.store.dto.request.CreateStoreDTO;
import com.example.umc7.domain.store.Store;
import com.example.umc7.domain.store.dto.response.StoreIdResponseDTO;

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
}