package com.careerzip.global.jwt.claims;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

class AccountClaimsTest {

    @ParameterizedTest
    @MethodSource("createAccountClaimWhenJobIsNullTestParams")
    @DisplayName("Job이 Null 일 경우 AccountClaim 생성 테스트")
    void createAccountClaimWhenJobIsNullTest(long id, String name, String email, String job, String role, String avatarUrl) {
        // given
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", id);
        claimsMap.put("name", name);
        claimsMap.put("email", email);
        claimsMap.put("job", job);
        claimsMap.put("role", role);
        claimsMap.put("avatarUrl", avatarUrl);

        Claims claims = Jwts.claims(claimsMap);

        // then
        assertThatCode(() -> AccountClaims.from(claims)).doesNotThrowAnyException();
    }

    private static Stream<Arguments> createAccountClaimWhenJobIsNullTestParams() {
        return Stream.of(
                Arguments.of(1L, "Username", "User email", null, "User role", "https://avatarUrl")
        );
    }
}
