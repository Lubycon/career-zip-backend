package com.careerzip.controller;

import com.careerzip.account.dto.request.AccountRequest;
import com.careerzip.account.dto.response.AccountSummary;
import com.careerzip.account.entity.Account;
import com.careerzip.account.service.AccountService;
import com.careerzip.global.error.response.ErrorCode;
import com.careerzip.global.jwt.JwtProperties;
import com.careerzip.global.jwt.JwtTokenProvider;
import com.careerzip.testconfig.base.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;

import static com.careerzip.testobject.account.AccountFactory.*;
import static com.careerzip.testobject.jwt.JwtFactory.createInValidJwtTokenOf;
import static com.careerzip.testobject.jwt.JwtFactory.createValidJwtTokenOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest extends BaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @SpyBean
    JwtTokenProvider jwtTokenProvider;

    @SpyBean
    JwtProperties jwtProperties;

    @Test
    @DisplayName("OK - OAuth 인증 API 테스트")
    void authorizeApiTest() throws Exception {
        // given
        Account member = createMemberOf(1L);
        AccountSummary account = AccountSummary.from(member);
        String headerPrefix = "Bearer ";
        String validJwtToken = createValidJwtTokenOf(account);
        AccountRequest accountRequest = createAccountRequest();

        // when
        when(accountService.find(any(AccountRequest.class))).thenReturn(account);

        ResultActions results = mockMvc.perform(post("/v1/accounts/auth")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header(HttpHeaders.AUTHORIZATION, headerPrefix + validJwtToken)
                                                .content(objectMapper.writeValueAsString(accountRequest)));

        // then
        results.andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("data").isNotEmpty());
    }

    @Test
    @DisplayName("BAD_REQUEST - 유효하지 않은 JWT Token 값이 들어왔을 경우 요청이 실패하는 테스트")
    void authorizeApiWhenInvalidJwtTokenTest() throws Exception {
        // given
        ErrorCode jwtInvalidationError = ErrorCode.JWT_INVALIDATION_ERROR;
        Account member = createMemberOf(1L);
        AccountSummary account = AccountSummary.from(member);
        String headerPrefix = "Bearer ";
        String validJwtToken = createInValidJwtTokenOf(account);
        AccountRequest accountRequest = createAccountRequest();

        // when
        when(accountService.find(any(AccountRequest.class))).thenReturn(account);

        ResultActions results = mockMvc.perform(post("/v1/accounts/auth")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header(HttpHeaders.AUTHORIZATION, headerPrefix + validJwtToken)
                                                .content(objectMapper.writeValueAsString(accountRequest)));

        // then
        results.andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("statusCode").value(jwtInvalidationError.getStatusCode()))
               .andExpect(jsonPath("message").value(jwtInvalidationError.getMessage()));
    }
}
