package com.example.umc7.domain.store.repository.querydsl;

import static com.example.umc7.domain.store.QStore.store;

import com.example.umc7.domain.store.Store;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Store> dynamicQueryWithBooleanBuilder(String name, Float score) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (name != null) {
            predicate.and(store.name.eq(name));
        }

        if (score != null) {
            predicate.and(store.score.goe(score));
        }

        return jpaQueryFactory
            .selectFrom(store)
            .where(predicate)
            .fetch();
    }
}