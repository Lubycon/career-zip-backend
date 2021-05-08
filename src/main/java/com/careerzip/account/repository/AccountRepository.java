package com.careerzip.account.repository;

import com.careerzip.account.entity.Account;
import com.careerzip.account.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.provider = :provider AND a.oauthId = :oauthId")
    Optional<Account> findByOAuth(@Param("provider") Provider provider, @Param("oauthId") String oauthId);
}
