package com.careerzip.controller.integration;

import com.careerzip.controller.mockmvc.MockMvcResponse;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.global.error.response.ErrorCode;
import com.careerzip.testconfig.base.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.careerzip.controller.mockmvc.MockMvcRequest.post;
import static com.careerzip.testobject.account.AccountFactory.createIntegrationTestMember;
import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.jwt.JwtFactory.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JwtIntegrationTest extends BaseIntegrationTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("OK - JWT 검증을 성공하는 테스트")
    void authorizeJwtTokenApiTest() throws Exception {
        // given
        String validJwtToken = createValidJwtTokenOf(createMember());

        // when
        accountRepository.save(createIntegrationTestMember());

        ResultActions results = mockMvc.perform(post("/v1/accounts/auth")
                                                .withToken(validJwtToken)
                                                .doRequest());

        // then
        results.andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("UNAUTHORIZED - 토큰 없이 요청을 할 경우 실패하는 테스트")
    void authorizeApiWhenNotJwtTokenTest() throws Exception {
        // given
        ErrorCode errorCode = ErrorCode.JWT_REQUIRED_ERROR;
        String jwtToken = "";

        // when
        ResultActions results = mockMvc.perform(post("/v1/accounts/auth")
                                                .withToken(jwtToken)
                                                .doRequest());

        // then
        MockMvcResponse.unAuthorized(results, errorCode);
    }

    @Test
    @DisplayName("BAD_REQUEST - 유효하지 않은 임시 토큰 값이 들어왔을 경우 요청이 실패하는 테스트")
    void authorizeApiWhenInvalidJwtTokenTest() throws Exception {
        // given
        ErrorCode errorCode = ErrorCode.JWT_INVALIDATION_ERROR;
        String invalidJwtToken = createInValidJwtTokenOf(createMember());

        // when
        ResultActions results = mockMvc.perform(post("/v1/accounts/auth")
                                                .withToken(invalidJwtToken)
                                                .doRequest());

        // then
        MockMvcResponse.badRequest(results, errorCode);
    }

    @Test
    @DisplayName("AUTHENTICATION_TIME_OUT - 만료된 임시 토큰 값이 들어왔을 경우 요청이 실패하는 테스트")
    void authorizeApiWhenExpiredJwtTokenTest() throws Exception {
        // given
        ErrorCode jwtExpiredError = ErrorCode.JWT_EXPIRED_ERROR;
        String expiredJwtToken = createExpiredJwtTokenOf(createMember());

        // when
        ResultActions results = mockMvc.perform(post("/v1/accounts/auth")
                                                .withToken(expiredJwtToken)
                                                .doRequest());

        // then
        results.andDo(print())
               .andExpect(status().is4xxClientError())
               .andExpect(jsonPath("statusCode").value(jwtExpiredError.getStatusCode()))
               .andExpect(jsonPath("message").value(jwtExpiredError.getMessage()));
    }
}
