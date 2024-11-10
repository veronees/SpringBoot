package com.example.umc7.domain.mission.repository;

import com.example.umc7.domain.mission.Mission;
import com.example.umc7.domain.mission.repository.querydsl.MissionQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long>, MissionQueryRepository {

}