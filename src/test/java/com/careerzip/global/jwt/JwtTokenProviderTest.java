package com.careerzip.global.jwt;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.global.error.exception.JwtValidationException;
import com.careerzip.global.error.exception.jwt.InvalidJwtTokenException;
import com.careerzip.global.error.exception.jwt.JwtExpirationException;
import com.careerzip.global.error.response.ErrorCode;
import com.careerzip.global.jwt.claims.AccountClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.jwt.JwtFactory.createExpiredJwtTokenOf;
import static com.careerzip.testobject.jwt.JwtFactory.createValidJwtProperties;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class JwtTokenProviderTest {

    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setup() {
        jwtTokenProvider = new JwtTokenProvider(createValidJwtProperties());
    }

    @Test
    @DisplayName("성공 - 유효한 임시 토큰을 검증하는 테스트")
    void validatePreAuthTokenTest() {
        // given
        Account account = createMember();
        String validPreAuthToken = jwtTokenProvider.issuePreAuthToken(account);

        // when
        Long accountId = jwtTokenProvider.parsePreAuthToken(validPreAuthToken);

        // then
        assertThat(account.getId()).isEqualTo(accountId);
    }

    @Test
    @DisplayName("에러 - Secret Key가 일치하지 않을 경우 실패하는 테스트")
    void invalidSecretKeyTest() {
        // given
        ErrorCode invalidJwtTokenError = ErrorCode.JWT_INVALIDATION_ERROR;
        String invalidToken = "Bearer " + "Secret key";

        // then
        assertThatThrownBy(() -> jwtTokenProvider.parsePreAuthToken(invalidToken))
                .isExactlyInstanceOf(InvalidJwtTokenException.class)
                .isInstanceOf(JwtValidationException.class)
                .hasMessage(invalidJwtTokenError.getMessage());
    }

    @Test
    @DisplayName("에러 - 만료된 임시 토큰으로 요청하는 경우 실패하는 테스트")
    void expiredPreAuthTokenTest() {
        // given
        ErrorCode jwtExpiredError = ErrorCode.JWT_EXPIRED_ERROR;
        Account account = createMember();
        String expiredToken = createExpiredJwtTokenOf(account);

        // then
        assertThatThrownBy(() -> jwtTokenProvider.parsePreAuthToken(expiredToken))
                .isExactlyInstanceOf(JwtExpirationException.class)
                .isInstanceOf(JwtValidationException.class)
                .hasMessage(jwtExpiredError.getMessage());
    }

    @Test
    @DisplayName("성공 - JWT 토큰을 생성 및 반환하는 테스트")
    void issueJwtToken() {
        // given
        Account account = createMember();

        // when
        String jwtToken = jwtTokenProvider.issueJwtToken(account);
        AccountClaims tokenBody = jwtTokenProvider.parseJwtToken(jwtToken);

        // then
        assertAll(
                () -> assertThat(tokenBody.getId()).isEqualTo(account.getId()),
                () -> assertThat(tokenBody.getName()).isEqualTo(account.getName()),
                () -> assertThat(tokenBody.getEmail()).isEqualTo(account.getEmail()),
                () -> assertThat(tokenBody.getAvatarUrl()).isEqualTo(account.getAvatarUrl()),
                () -> assertThat(tokenBody.getRole()).isEqualTo(account.getRole().name())
        );
    }

    @ParameterizedTest
    @MethodSource("invalidAuthorizationHeaderParameters")
    @DisplayName("에러 - 유효하지 않은 Authorization 헤더로 요청 했을 때 실패하는 테스트")
    void invalidAuthorizationHeaderTest(String wrongHeader) {
        // given
        ErrorCode invalidJwtTokenError = ErrorCode.JWT_INVALIDATION_ERROR;

        // then
        assertThatThrownBy(() -> jwtTokenProvider.parsePreAuthToken(wrongHeader))
                .isExactlyInstanceOf(InvalidJwtTokenException.class)
                .isInstanceOf(JwtValidationException.class)
                .hasMessage(invalidJwtTokenError.getMessage());
    }

    private static Stream<Arguments> invalidAuthorizationHeaderParameters() {
        return Stream.of(
                Arguments.of("Wrong Header"), Arguments.of("Bearer")
        );
    }
}
