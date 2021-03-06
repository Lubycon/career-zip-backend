package com.careerzip.domain.account.repository;

import com.careerzip.domain.account.dto.request.AccountRequest;
import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.entity.Provider;
import com.careerzip.global.error.exception.entity.AccountNotFoundException;
import com.careerzip.security.oauth.dto.OAuthAccount;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static com.careerzip.testobject.account.AccountFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

class AccountRepositoryTest extends BaseRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("성공 - Account 생성 기본 값(submitCount, deleted) 설정 테스트")
    void accountDefaultValueTest() {
        // given
        int defaultSubmitCount = 0;
        Account account = createJpaMember();

        // when
        Account savedAccount = accountRepository.save(account);

        // then
        assertThat(savedAccount.getSubmitCount()).isEqualTo(defaultSubmitCount);
        assertThat(savedAccount.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("성공 - Account 시간 생성 (Jpa Auditing) 테스트")
    void jpaAuditingCreatedDateTimeTest() {
        // given
        Account account = createJpaMember();

        // when
        LocalDateTime testTime = LocalDateTime.now();
        Account savedAccount = accountRepository.save(account);

        // then
        assertThat(savedAccount.getCreatedDateTime()).isAfter(testTime);
        assertThat(savedAccount.getUpdatedDateTime()).isAfter(testTime);
    }

    @Test
    @DisplayName("OAuth 관련 데이터로 사용자를 조회하는 메서드 테스트")
    void findByOAuthTest() {
        // given
        Account account = createJpaMember();
        OAuthAccount oAuthAccount = createOAuthAccountOf(account);
        Account newAccount = accountRepository.save(account);

        // when
        Account foundAccount = accountRepository.findByOAuth(oAuthAccount.getProvider(), oAuthAccount.getOAuthId())
                                                .orElseThrow(AccountNotFoundException::new);

        // then
        assertThat(foundAccount).usingRecursiveComparison().isEqualTo(newAccount);
    }
}
