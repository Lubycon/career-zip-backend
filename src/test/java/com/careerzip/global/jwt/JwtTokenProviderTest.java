package com.careerzip.global.jwt;

import com.careerzip.account.entity.Account;
import com.careerzip.global.error.jwt.JwtValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.stream.Stream;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static org.assertj.core.api.Assertions.*;
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

    @ParameterizedTest
    @MethodSource("invalidAuthorizationHeaderParameters")
    @DisplayName("에러 - 유효하지 않은 Authorization 헤더로 요청 했을 때 실패하는 테스트")
    void invalidAuthorizationHeaderTest(String wrongHeader) {
        // when

        // then
        assertThatThrownBy(() -> jwtTokenProvider.validateAuthorizationToken(wrongHeader))
                .isInstanceOf(JwtValidationException.class);
    }

    private static Stream<Arguments> invalidAuthorizationHeaderParameters() {
        return Stream.of(
                Arguments.of("Wrong Header"), Arguments.of("Bearer")
        );
    }
}
