package com.careerzip.domain.account.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.entity.Provider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.careerzip.domain.account.entity.QAccount.account;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Account> findByOAuth(Provider provider, String oAuthId) {
        return Optional.ofNullable(queryFactory.selectFrom(account)
                                               .where(account.provider.eq(provider),
                                                      account.oAuthId.eq(oAuthId))
                                               .fetchOne());
    }
}