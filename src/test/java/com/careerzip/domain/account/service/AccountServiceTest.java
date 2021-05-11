package com.careerzip.domain.account.service;

import com.careerzip.domain.account.dto.request.AccountRequest;
import com.careerzip.domain.account.dto.response.AccountSummary;
import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.entity.Provider;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.global.error.exception.AuthException;
import com.careerzip.global.error.exception.auth.InvalidOAuthProviderException;
import com.careerzip.global.error.response.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.careerzip.testobject.account.AccountFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    AccountRepository accountRepository;

    @Test
    @DisplayName("성공 - 요청 받은 AccountRequest DTO를 바탕으로 Account를 반환하는 테스트")
    void findAccountTest() {
        // given
        AccountRequest accountRequest = createAccountRequest();
        Account account = createMemberOf(accountRequest);

        // when
        when(accountRepository.findByOAuth(Provider.valueOf(accountRequest.getProvider()), accountRequest.getOAuthId()))
                .thenReturn(Optional.of(account));

        AccountSummary foundAccount = accountService.find(accountRequest);

        // then
        assertAll(
                () -> assertThat(foundAccount.getId()).isEqualTo(account.getId()),
                () -> assertThat(foundAccount.getName()).isEqualTo(account.getName()),
                () -> assertThat(foundAccount.getEmail()).isEqualTo(account.getEmail()),
                () -> assertThat(foundAccount.getAvatarUrl()).isEqualTo(account.getAvatarUrl()),
                () -> assertThat(foundAccount.getRole()).isEqualTo(account.getRole())
        );
    }

    @Test
    @DisplayName("에러 - 인증시 존재하지 않는 Provider 데이터가 DTO에 포함될 경우 요청이 실패하는 테스트")
    void findAccountWhenInvalidOAuthProviderTest() {
        // given
        ErrorCode invalidOAuthProviderError = ErrorCode.INVALID_OAUTH_PROVIDER_ERROR;
        AccountRequest accountRequest = createAccountRequestOf("InvalidProvider");

        // then
        assertThatThrownBy(() -> accountService.find(accountRequest))
                .isExactlyInstanceOf(InvalidOAuthProviderException.class)
                .isInstanceOf(AuthException.class)
                .hasMessage(invalidOAuthProviderError.getMessage());
    }
}
