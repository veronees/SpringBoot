package com.example.umc7.domain.mission.service;

import com.example.umc7.domain.mission.Mission;
import com.example.umc7.domain.mission.repository.MissionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;

    public List<Mission> findMissionList() {
        return missionRepository.findMissionByRegion("서울", 0, 10);
    }
}