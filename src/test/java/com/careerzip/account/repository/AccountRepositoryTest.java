package com.careerzip.account.repository;

import com.careerzip.account.dto.request.AccountRequest;
import com.careerzip.account.dto.request.AccountRequestBuilder;
import com.careerzip.account.entity.Account;
import com.careerzip.account.entity.Provider;
import com.careerzip.account.entity.Role;
import com.careerzip.global.error.exception.EntityNotFoundException;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest extends BaseRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("성공 - Account 생성 기본 값(submitCount, deleted) 설정 테스트")
    void accountDefaultValueTest() {
        // given
        int defaultSubmitCount = 0;
        Account account = createMember();

        // when
        Account savedAccount = accountRepository.save(account);

        // then
        assertThat(savedAccount.getSubmitCount()).isEqualTo(defaultSubmitCount);
        assertThat(savedAccount.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("성공 - Account 생성 시간 (Jpa Auditing) 테스트")
    void jpaAuditingTest() {
        // given
        Account account = createMember();

        // when
        LocalDateTime testTime = LocalDateTime.now();
        Account savedAccount = accountRepository.save(account);

        // then
        assertThat(savedAccount.getCreatedDateTime()).isAfter(testTime);
    }

    @Test
    @DisplayName("OAuth 관련 데이터로 사용자를 조회하는 메서드 테스트")
    void findByOAuthTest() {
        // given
        AccountRequest accountRequest = AccountRequestBuilder.newBuilder()
                                                             .provider("GOOGLE")
                                                             .oAuthId("OAuthID")
                                                             .name("Username")
                                                             .email("account@email.com")
                                                             .avatarUrl("https://avatarUrl")
                                                             .build();

        Account account = Account.from(accountRequest);
        Account newAccount = accountRepository.save(account);

        // when
        Account foundAccount = accountRepository.findByOAuth(Provider.valueOf(accountRequest.getProvider()), accountRequest.getOAuthId())
                .orElseThrow(EntityNotFoundException::new);

        // then
        assertThat(foundAccount).usingRecursiveComparison().isEqualTo(newAccount);
    }
}
