package com.example.umc7.domain.store;

import com.example.umc7.apipayload.ApiResponse;
import com.example.umc7.domain.store.dto.request.CreateStoreDTO;
import com.example.umc7.domain.store.dto.response.StoreIdResponseDTO;
import com.example.umc7.domain.store.service.StoreCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreCommandService storeCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<StoreIdResponseDTO>> createStore(
        @RequestBody CreateStoreDTO createStoreDTO) {
        return ResponseEntity.ok(
            ApiResponse.onSuccess(storeCommandService.saveStore(createStoreDTO)));
    }
}