package com.example.umc7.domain.store.controller;

import com.example.umc7.apipayload.ApiResponse;
import com.example.umc7.apipayload.PageResponseDTO;
import com.example.umc7.domain.review.dto.response.ReviewResponseDTO;
import com.example.umc7.domain.store.dto.request.CreateStoreDTO;
import com.example.umc7.domain.store.dto.response.StoreIdResponseDTO;
import com.example.umc7.domain.store.dto.response.StoreMissionResponseDto;
import com.example.umc7.domain.store.service.StoreCommandService;
import com.example.umc7.domain.store.service.StoreQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreCommandService storeCommandService;
    private final StoreQueryService storeQueryService;

    @PostMapping
    public ResponseEntity<ApiResponse<StoreIdResponseDTO>> createStore(
        @RequestBody CreateStoreDTO createStoreDTO) {
        return ResponseEntity.ok(
            ApiResponse.onSuccess(storeCommandService.saveStore(createStoreDTO)));
    }

    @Operation(
        summary = "특정 가게의 미션 목록 조회 API",
        description = "특정 가게의 미션 목록 조회 API입니다. 해당 API는 사용자 인증이 요구됩니다.\n\n",
        parameters = {
            @Parameter(name = "page", description = "조회할 페이지 번호, 미입력시 기본값 1", required = false),
            @Parameter(name = "size", description = "조회할 페이지의 크기, 미입력시 기본값 10", required = false)
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "COMMON200",
                description = "특정 가게의 미션 목록을 페이징 처리해 반환",
                content = @Content(schema = @Schema(implementation = ReviewResponseDTO.class))
            )
        }
    )
    @GetMapping("/{storeId}/missions")
    public ResponseEntity<ApiResponse<PageResponseDTO<List<StoreMissionResponseDto>>>> getStoreMissionList(
        @PathVariable Long storeId, @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
            ApiResponse.onSuccess(storeQueryService.getStoreMissionList(storeId, page, size)));
    }
}