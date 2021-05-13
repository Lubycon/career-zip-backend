package com.careerzip.domain.account.entity;

import com.careerzip.global.error.exception.AuthException;
import com.careerzip.global.error.exception.auth.InvalidOAuthProviderException;
import com.careerzip.global.error.response.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProviderTest {

    @Test
    @DisplayName("유효하지 않은 OAuth Provider 값을 검사하는 메서드 테스트")
    void proveInvalidProviderTest() {
        // given
        String wrongProvider = "InvalidProvider";

        // when
        boolean invalidProvider = Provider.isInvalidProvider(wrongProvider);

        // then
        assertThat(invalidProvider).isTrue();
    }

    @Test
    @DisplayName("성공 - OAuth Provider 문자열을 Enum으로 반환하는 테스트")
    void mapToValueTest() {
        // given
        String google = "google";

        // when
        Provider provider = Provider.mapToValue(google);

        // then
        assertThat(provider).isEqualTo(Provider.GOOGLE);
    }

    @Test
    @DisplayName("에러 - 유효하지 않은 OAuth Provider 문자열을 Enum으로 변경 시도하면 에러가 발생하는 테스트")
    void mapToInvalidValueTest() {
        // given
        ErrorCode errorCode = ErrorCode.INVALID_OAUTH_PROVIDER_ERROR;
        String wrongProvider = "WrongProvider";

        // then
        assertThatThrownBy(() -> Provider.mapToValue(wrongProvider))
                .isExactlyInstanceOf(InvalidOAuthProviderException.class)
                .isInstanceOf(AuthException.class)
                .hasMessage(errorCode.getMessage());
    }
}
