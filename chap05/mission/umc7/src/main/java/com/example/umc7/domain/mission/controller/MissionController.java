package com.example.umc7.domain.mission.controller;

import com.example.umc7.apipayload.ApiResponse;
import com.example.umc7.domain.mission.dto.request.CreateMissionDTO;
import com.example.umc7.domain.mission.service.MissionCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController {

    private final MissionCommandService missionCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveMission(
        @RequestBody CreateMissionDTO createMissionDTO) {
        missionCommandService.saveMission(createMissionDTO);
        return ResponseEntity.ok(ApiResponse.onSuccess("미션 추가 완료"));
    }
}