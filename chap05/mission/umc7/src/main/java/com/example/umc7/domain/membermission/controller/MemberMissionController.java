package com.example.umc7.domain.membermission.controller;

import com.example.umc7.apipayload.ApiResponse;
import com.example.umc7.domain.membermission.dto.request.CreateMemberMissionDTO;
import com.example.umc7.domain.membermission.service.MemberMissionCommandService;
import com.example.umc7.domain.mission.dto.request.CreateMissionDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-missions")
public class MemberMissionController {

    private final MemberMissionCommandService memberMissionCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveMemberMission(
        @RequestBody @Valid CreateMemberMissionDTO createMemberMissionDTO) {
        memberMissionCommandService.saveMemberMission(createMemberMissionDTO);
        return ResponseEntity.ok(ApiResponse.onSuccess("사용자에게 미션 추가 완료"));
    }
}
