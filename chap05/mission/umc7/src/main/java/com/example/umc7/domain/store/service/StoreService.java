package com.example.umc7.domain.store.service;

import com.example.umc7.domain.store.Store;
import com.example.umc7.domain.store.repository.StoreRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    public Optional<Store> findStore(Long id) {
        return storeRepository.findById(id);
    }

    public List<Store> findStoresByNameAndScore(String name, Float score) {
        List<Store> filteredScores = storeRepository.dynamicQueryWithBooleanBuilder(name, score);

        filteredScores.forEach(store -> log.info("Store: {}", store.getName()));

        return filteredScores;
    }
}
