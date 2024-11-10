package com.example.umc7.domain.store.repository;

import com.example.umc7.domain.store.Store;
import com.example.umc7.domain.store.repository.querydsl.StoreQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreQueryRepository {

}