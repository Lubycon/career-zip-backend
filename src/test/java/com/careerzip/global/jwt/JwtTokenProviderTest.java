package com.careerzip.global.jwt;

import com.careerzip.account.entity.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @InjectMocks
    JwtTokenProvider jwtTokenProvider;

    @Mock
    JwtProperties jwtProperties;

    @Mock
    Account mockAccount;

    @Test
    @DisplayName("성공 - 유효한 인증 토큰을 검증하는 테스트")
    void validateAuthorizationTokenTest() {
        // given
        Account account = createMember();

        // when
        when(mockAccount.getId()).thenReturn(account.getId());
        when(mockAccount.getEmail()).thenReturn(account.getEmail());
        when(mockAccount.getRole()).thenReturn(account.getRole());

        when(jwtProperties.getIssuer()).thenReturn("Test Issuer");
        when(jwtProperties.getExpiration()).thenReturn(10000L);
        when(jwtProperties.getSecretKey()).thenReturn("Test Secret Key");

        String jwtToken = jwtTokenProvider.issueToken(mockAccount);
        String header = "Bearer " + jwtToken;

        // then
        assertThatCode(() -> jwtTokenProvider.validateAuthorizationToken(header)).doesNotThrowAnyException();
    }
}
