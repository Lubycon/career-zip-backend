package com.careerzip.account.service;

import com.careerzip.account.dto.request.AccountRequest;
import com.careerzip.account.dto.request.AccountRequestBuilder;
import com.careerzip.account.entity.Account;
import com.careerzip.account.entity.Provider;
import com.careerzip.account.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        AccountRequest accountRequest = AccountRequestBuilder.newBuilder()
                                                             .provider("GOOGLE")
                                                             .oAuthId("OAuthID")
                                                             .name("Username")
                                                             .email("account@email.com")
                                                             .avatarUrl("https://avatarUrl")
                                                             .build();
        Account account = Account.from(accountRequest);

        // when
        when(accountRepository.findByOAuth(Provider.valueOf(accountRequest.getProvider()), accountRequest.getOAuthId()))
                .thenReturn(Optional.of(account));

        Account foundAccount = accountService.find(accountRequest);

        // then
        assertThat(foundAccount).usingRecursiveComparison().isEqualTo(account);
    }
}
