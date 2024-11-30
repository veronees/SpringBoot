package com.example.umc7.apipayload;

import lombok.Builder;

@Builder
public record PageResponseDTO<T>(

    int page,
    boolean hasNext,
    T result
) {

}