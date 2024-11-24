package com.example.umc7.domain.store.dto.request;

public record CreateStoreDTO(
    String region,
    String name,
    String address,
    float score
) {

}