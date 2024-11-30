package com.example.umc7.domain.membermission.controller;

import com.example.umc7.apipayload.ApiResponse;
import com.example.umc7.apipayload.PageResponseDTO;
import com.example.umc7.domain.membermission.dto.request.CreateMemberMissionDTO;
import com.example.umc7.domain.membermission.dto.response.MemberMissionResponseDTO;
import com.example.umc7.domain.membermission.service.MemberMissionCommandService;
import com.example.umc7.domain.membermission.service.MemberMissionQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-missions")
public class MemberMissionController {

    private final MemberMissionCommandService memberMissionCommandService;
    private final MemberMissionQueryService memberMissionQueryService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveMemberMission(
        @RequestBody @Valid CreateMemberMissionDTO createMemberMissionDTO) {
        memberMissionCommandService.saveMemberMission(createMemberMissionDTO);
        return ResponseEntity.ok(ApiResponse.onSuccess("사용자에게 미션 추가 완료"));
    }


    @Operation(
        summary = "진행 중인 미션 조회 API",
        description = "진행 중인 미션 조회 API입니다. 해당 API는 사용자 인증이 요구됩니다.\n\n",
        parameters = {
            @Parameter(name = "page", description = "조회할 페이지 번호, 미입력시 기본값 1", required = false),
            @Parameter(name = "size", description = "조회할 페이지의 크기, 미입력시 기본값 10", required = false)
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "COMMON200",
                description = "진행 중인 미션을 페이징 처리해 반환",
                content = @Content(schema = @Schema(implementation = MemberMissionResponseDTO.class))
            )
        }
    )
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<PageResponseDTO<List<MemberMissionResponseDTO>>>> getChallengingMissionList(
        @PathVariable Long memberId, @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
            ApiResponse.onSuccess(memberMissionQueryService.getChallengingMissionList(memberId, page, size)));
    }

    @Operation(
        summary = "진행 중인 미션을 진행 완료로 수정 API",
        description = "진행 중인 미션을 진행 완료로 수정하는 API입니다. 해당 API는 사용자 인증이 요구됩니다.\n\n",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "COMMON200",
                description = "수정 성공 결과를 반환",
                content = @Content(schema = @Schema(implementation = String.class))
            )
        }
    )
    @PatchMapping("/{memberMissionId}")
    public ResponseEntity<ApiResponse<String>> modifyClearStatus(@PathVariable Long memberMissionId) {
        memberMissionCommandService.modifyClearStatus(memberMissionId);
        return ResponseEntity.ok(ApiResponse.onSuccess("수정 완료"));
    }
}