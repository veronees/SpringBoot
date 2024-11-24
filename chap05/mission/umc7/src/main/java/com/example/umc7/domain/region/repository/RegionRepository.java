package com.example.umc7.domain.region.repository;

import com.example.umc7.domain.region.Region;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findByName(String region);
}
