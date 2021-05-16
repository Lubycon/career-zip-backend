package com.careerzip.domain.account.service;

import com.careerzip.domain.account.dto.request.AccountUpdateRequest;
import com.careerzip.domain.account.dto.response.AccountSummary;
import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.global.error.exception.BusinessException;
import com.careerzip.global.error.exception.business.AccountMismatchException;
import com.careerzip.global.error.response.ErrorCode;
import com.careerzip.global.jwt.JwtTokenProvider;
import com.careerzip.security.oauth.dto.OAuthAccount;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    AccountRepository accountRepository;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("성공 - 회원 정보 수정 메서드 테스트")
    void updateTest() {
        // given
        Account account = createMember();
        OAuthAccount loginAccount = createOAuthAccountOf(account);
        Long accountId = account.getId();

        AccountUpdateRequest updateRequest = createAccountUpdateRequest("newName", "newEmail");

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        AccountSummary updatedAccount = accountService.update(loginAccount, accountId, updateRequest);

        // then
        assertThat(updatedAccount.getName()).isEqualTo(updateRequest.getName());
        assertThat(updatedAccount.getEmail()).isEqualTo(updateRequest.getEmail());
    }

    @Test
    @DisplayName("에러 - 일치하지 않는 Account로 회원 정보 수정 요청을 했을 때 예외가 발생하는 테스트")
    void update() {
        // given
        ErrorCode errorCode = ErrorCode.ACCOUNT_MISMATCH_EXCEPTION;
        Account account = createMember();
        OAuthAccount loginAccount = createOAuthAccountOf(account);
        Long differentId = account.getId() + 1;
        AccountUpdateRequest updateRequest = createAccountUpdateRequest("newName", "newEmail");

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));

        // then
        assertThatThrownBy(() -> accountService.update(loginAccount, differentId, updateRequest))
                .isExactlyInstanceOf(AccountMismatchException.class)
                .isInstanceOf(BusinessException.class)
                .hasMessage(errorCode.getMessage());
    }
}
