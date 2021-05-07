package com.careerzip.account.repository;

import com.careerzip.account.entity.Account;
import com.careerzip.account.entity.Provider;
import com.careerzip.account.entity.Role;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest extends BaseRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("성공 - Account 생성시 기본 값이(submitCount, deleted) 설정되는 테스트")
    void accountDefaultValueTest() {
        // given
        Account account = Account.builder()
                                 .oauthId("OAuth ID")
                                 .provider(Provider.GOOGLE)
                                 .name("Account Name")
                                 .email("Email")
                                 .avatarUrl("https://avatarUrl")
                                 .role(Role.MEMBER)
                                 .build();
        int defaultSubmitCount = 0;

        // when
        Account savedAccount = accountRepository.save(account);

        // then
        assertThat(savedAccount.getSubmitCount()).isEqualTo(defaultSubmitCount);
        assertThat(savedAccount.isDeleted()).isFalse();
    }
}
