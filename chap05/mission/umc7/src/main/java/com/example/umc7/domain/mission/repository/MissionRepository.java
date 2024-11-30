package com.example.umc7.domain.mission.repository;

import com.example.umc7.domain.mission.Mission;
import com.example.umc7.domain.mission.repository.querydsl.MissionQueryRepository;
import com.example.umc7.domain.store.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long>, MissionQueryRepository {

    Page<Mission> findByStore(Store store, Pageable pageable);
}