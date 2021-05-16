package com.careerzip.controller.account;

import com.careerzip.controller.AccountController;
import com.careerzip.controller.mockmvc.MockMvcRequest;
import com.careerzip.controller.mockmvc.MockMvcResponse;
import com.careerzip.domain.account.dto.request.AccountUpdateRequest;
import com.careerzip.domain.account.dto.response.AccountSummary;
import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.service.AccountService;
import com.careerzip.global.error.exception.jwt.InvalidJwtTokenException;
import com.careerzip.global.error.exception.jwt.JwtExpirationException;
import com.careerzip.global.error.response.ErrorCode;
import com.careerzip.global.jwt.JwtProperties;
import com.careerzip.global.jwt.JwtTokenProvider;
import com.careerzip.security.SecurityConfig;
import com.careerzip.security.oauth.dto.OAuthAccount;
import com.careerzip.testconfig.base.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.Stream;

import static com.careerzip.testobject.account.AccountFactory.*;
import static com.careerzip.testobject.jwt.JwtFactory.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class,
            excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class))
class AccountControllerTest extends BaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @SpyBean
    JwtTokenProvider jwtTokenProvider;

    @SpyBean
    JwtProperties jwtProperties;

    @WithAnonymousUser
    @Test
    @DisplayName("NO_CONTENT - JWT 토큰 발급 API 테스트")
    void authorizeApiTest() throws Exception {
        // given
        Account account = createMember();
        String validJwtToken = createValidJwtTokenOf(account);

        // when
        ResultActions results = mockMvc.perform(post("/v1/accounts/auth")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header(HttpHeaders.AUTHORIZATION, validJwtToken));


        // then
        results.andDo(print())
               .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("BAD_REQUEST - 유효하지 않은 임시 토큰 값이 들어왔을 경우 요청이 실패하는 테스트")
    void authorizeApiWhenInvalidJwtTokenTest() throws Exception {
        // given
        ErrorCode jwtInvalidationError = ErrorCode.JWT_INVALIDATION_ERROR;
        Account account = createMember();
        String invalidPreAuthToken = createInValidJwtTokenOf(account);

        // when
        when(accountService.issueJwtToken(invalidPreAuthToken)).thenThrow(new InvalidJwtTokenException());

        ResultActions results = mockMvc.perform(post("/v1/accounts/auth")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header(HttpHeaders.AUTHORIZATION, invalidPreAuthToken));

        // then
        results.andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("statusCode").value(jwtInvalidationError.getStatusCode()))
               .andExpect(jsonPath("message").value(jwtInvalidationError.getMessage()));
    }

    @Test
    @DisplayName("AUTHENTICATION_TIME_OUT - 만료된 임시 토큰 값이 들어왔을 경우 요청이 실패하는 테스트")
    void authorizeApiWhenExpiredJwtTokenTest() throws Exception {
        // given
        ErrorCode jwtExpiredError = ErrorCode.JWT_EXPIRED_ERROR;
        Account account = createMember();
        String expiredPreAuthToken = createExpiredJwtTokenOf(account);

        // when
        when(accountService.issueJwtToken(expiredPreAuthToken)).thenThrow(new JwtExpirationException());
        ResultActions results = mockMvc.perform(post("/v1/accounts/auth")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header(HttpHeaders.AUTHORIZATION, expiredPreAuthToken));

        // then
        results.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("statusCode").value(jwtExpiredError.getStatusCode()))
                .andExpect(jsonPath("message").value(jwtExpiredError.getMessage()));
    }

    @ParameterizedTest
    @MethodSource("updateAccountWhenInvalidInputTestParams")
    @DisplayName("BAD_REQUEST - 올바르지 않은 Account 수정 요청이 들어왔을 때 에러가 반환되는 테스트")
    void updateAccountWhenInvalidInputTest(String name, String email) throws Exception {
        // given
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_ERROR;
        Account account = createMember();
        String validJwtToken = createValidJwtTokenOf(account);
        OAuthAccount loginAccount = createOAuthAccountOf(account);
        AccountUpdateRequest updateRequest = createAccountUpdateRequest(name, email);

        // when
        when(accountService.update(refEq(loginAccount), eq(account.getId()), refEq(updateRequest))).thenReturn(AccountSummary.from(account));

        ResultActions results = mockMvc.perform(MockMvcRequest.put("/v1/accounts/{id}", account.getId())
                                                .withBody(objectMapper.writeValueAsString(updateRequest))
                                                .withToken(validJwtToken)
                                                .withPrincipal(loginAccount)
                                                .doRequest());

        // then
        MockMvcResponse.badRequest(results, errorCode);
    }

    private static Stream<Arguments> updateAccountWhenInvalidInputTestParams() {
        return Stream.of(
                Arguments.of("Something Invalid Length", "email@email.com"),
                Arguments.of("something", null),
                Arguments.of("somethingNew", "SomethingNotEmail")
        );
    }
}
