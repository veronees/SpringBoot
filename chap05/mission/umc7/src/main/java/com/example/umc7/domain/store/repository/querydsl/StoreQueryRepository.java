package com.example.umc7.domain.store.repository.querydsl;

import com.example.umc7.domain.store.Store;
import java.util.List;

public interface StoreQueryRepository {

    List<Store> dynamicQueryWithBooleanBuilder(String name, Float score);
}