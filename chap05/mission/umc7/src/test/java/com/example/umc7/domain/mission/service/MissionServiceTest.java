package com.example.umc7.domain.mission.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.umc7.domain.mission.Mission;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MissionServiceTest {

    @Autowired
    MissionService missionService;
    
    @Test
    @DisplayName("미션을 조회한다")
    void test() {
        List<Mission> missionList = missionService.findMissionList();

        for (Mission mission : missionList) {
            System.out.println("mission.getStore().getName() = " + mission.getStore().getName());
            System.out.println("mission.getReward() = " + mission.getReward());
        }
    }
}