package com.careerzip.domain.account.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}
