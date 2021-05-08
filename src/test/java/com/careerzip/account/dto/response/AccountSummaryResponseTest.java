package com.careerzip.account.dto.response;

import com.careerzip.account.entity.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.account.AccountFactory.createMemberOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountSummaryResponseTest {

    @Test
    @DisplayName("Account를 받아서 정적 팩토리 메서드로부터 DTO를 생성하는 테스트")
    void createAccountSummaryResponse() {
        // given
        Account account = createMemberOf(1L);

        // when
        AccountSummaryResponse response = AccountSummaryResponse.from(account);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(account.getId()),
                () -> assertThat(response.getName()).isEqualTo(account.getName()),
                () -> assertThat(response.getEmail()).isEqualTo(account.getEmail()),
                () -> assertThat(response.getAvatarUrl()).isEqualTo(account.getAvatarUrl())
        );
    }
}
