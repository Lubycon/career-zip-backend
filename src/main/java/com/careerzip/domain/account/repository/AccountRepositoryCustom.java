package com.careerzip.domain.account.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.entity.Provider;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountRepositoryCustom {

    Optional<Account> findByOAuth(Provider provider, String oAuthId);

    List<Account> findAllBy(LocalDateTime startDateTIme, LocalDateTime endDateTime);
}
