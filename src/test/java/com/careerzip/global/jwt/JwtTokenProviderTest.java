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
import static com.careerzip.testobject.jwt.JwtFactory.createExpiredJwtProperties;
import static com.careerzip.testobject.jwt.JwtFactory.createValidJwtProperties;
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
        JwtProperties validProperties = createValidJwtProperties();

        // when
        when(mockAccount.getId()).thenReturn(account.getId());
        when(mockAccount.getEmail()).thenReturn(account.getEmail());
        when(mockAccount.getRole()).thenReturn(account.getRole());

        when(jwtProperties.getIssuer()).thenReturn(validProperties.getIssuer());
        when(jwtProperties.getExpiration()).thenReturn(validProperties.getExpiration());
        when(jwtProperties.getSecretKey()).thenReturn(validProperties.getSecretKey());

        String jwtToken = jwtTokenProvider.issueToken(mockAccount);
        String header = "Bearer " + jwtToken;

        // then
        assertThatCode(() -> jwtTokenProvider.validateAuthorizationToken(header)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("에러 - Secret Key가 일치하지 않을 경우 실패하는 테스트")
    void invalidSecretKeyTest() {
        // given
        String invalidToken = "Bearer " + "Secret key";

        // when
        when(jwtProperties.getSecretKey()).thenReturn("Different key");

        // then
        assertThatThrownBy(() -> jwtTokenProvider.validateAuthorizationToken(invalidToken))
                .isInstanceOf(JwtValidationException.class);
    }

    @Test
    @DisplayName("에러 - 만료된 인증 토큰으로 요청하는 경우 실패하는 테스트")
    void expiredTokenTest() {
        // given
        JwtProperties expiredProperties = createExpiredJwtProperties();
        Account account = createMember();

        // when
        when(jwtProperties.getSecretKey()).thenReturn(expiredProperties.getSecretKey());
        when(jwtProperties.getIssuer()).thenReturn(expiredProperties.getIssuer());
        when(jwtProperties.getExpiration()).thenReturn(expiredProperties.getExpiration());

        // when
        when(mockAccount.getId()).thenReturn(account.getId());
        when(mockAccount.getEmail()).thenReturn(account.getEmail());
        when(mockAccount.getRole()).thenReturn(account.getRole());

        String jwtToken = jwtTokenProvider.issueToken(mockAccount);
        String authorizationHeader = "Bearer " + jwtToken;

        // then
        assertThatThrownBy(() -> jwtTokenProvider.validateAuthorizationToken(authorizationHeader));
    }

    @ParameterizedTest
    @MethodSource("invalidAuthorizationHeaderParameters")
    @DisplayName("에러 - 유효하지 않은 Authorization 헤더로 요청 했을 때 실패하는 테스트")
    void invalidAuthorizationHeaderTest(String wrongHeader) {

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
