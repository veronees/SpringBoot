package com.example.umc7.domain.store.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.umc7.domain.store.Store;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Rollback(value = false)
class StoreServiceTest {

    @Autowired
    StoreService storeService;


    @Test
    @DisplayName("가게 id로 가게를 조회한다")
    void test() {
        Optional<Store> store = storeService.findStore(1L);

        if (store.isPresent()) {
            System.out.println("store name: " + store.get().getName());
        } else {
            System.out.println("가게 이름 없음");
        }
    }

    @Test
    @DisplayName("이름과 점수로 가게를 조회한다")
    void test2() {
        String storeName = "요아정";
        Float storeScore = 4.0f;
        List<Store> findStoreList = storeService.findStoresByNameAndScore(storeName,
            storeScore);

        assertThat(findStoreList.size()).isEqualTo(2);
    }
}