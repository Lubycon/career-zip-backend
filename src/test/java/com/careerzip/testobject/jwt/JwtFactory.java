package com.careerzip.testobject.jwt;

import com.careerzip.domain.account.dto.response.AccountSummary;
import com.careerzip.domain.account.entity.Account;
import com.careerzip.global.jwt.JwtProperties;
import com.careerzip.global.jwt.JwtTokenProvider;

public class JwtFactory {

    /*
     *
     * createMockObject()
     *   - Test parameter 에 의존하지 않는 테스트 객체를 생성 합니다.
     *     (ex. Mocking 에서 자주 사용되는 테스트 객체)
     *
     *
     * createMockObjectOf(T parameter1, T parameter2, ...)
     *   - Test parameter 에 의존하는 테스트 객체를 생성 합니다.
     *     (ex. JPA, 예외 검증, 경우의 수를 따져야 하는 테스트)
     *
     *
     */

    // JwtProperties - Valid
    public static JwtProperties createValidJwtProperties() {
        return new JwtProperties("TestIssuer", "TestSecretKey", "10000",
                 "5000",  "jwt", "http://redirectUrl");
    }

    // JwtProperties - Invalid
    public static JwtProperties createInValidJwtProperties() {
        return new JwtProperties("Invalid Issuer", "Invalid Secret Key", "10000",
                 "5000","jwt", "http://redirectUrl");
    }

    // JwtProperties - Expired
    public static JwtProperties createExpiredJwtProperties() {
        return new JwtProperties("TestIssuer", "TestSecretKey", "10",
                 "10", "jwt", "http://redirectUrl");
    }

    // JwtToken - Valid
    public static String createValidJwtTokenOf(Account account) {
        JwtProperties jwtProperties = createValidJwtProperties();
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(jwtProperties);
        return jwtTokenProvider.issueToken(account);
    }

    // JwtToken - Invalid
    public static String createInValidJwtTokenOf(Account account) {
        JwtProperties jwtProperties = createInValidJwtProperties();
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(jwtProperties);
        return jwtTokenProvider.issueToken(account);
    }

    // JwtToken - Expired
    public static String createExpiredJwtTokenOf(Account account) {
        JwtProperties jwtProperties = createExpiredJwtProperties();
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(jwtProperties);
        return jwtTokenProvider.issueToken(account);
    }
}
