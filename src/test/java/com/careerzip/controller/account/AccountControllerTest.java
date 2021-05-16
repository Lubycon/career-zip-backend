package com.careerzip.controller.account;

import com.careerzip.controller.AccountController;
import com.careerzip.controller.mockmvc.MockMvcRequest;
import com.careerzip.controller.mockmvc.MockMvcResponse;
import com.careerzip.domain.account.dto.request.AccountUpdateRequest;
import com.careerzip.domain.account.dto.response.AccountSummary;
import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.service.AccountService;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.Stream;

import static com.careerzip.testobject.account.AccountFactory.*;
import static com.careerzip.testobject.jwt.JwtFactory.createValidJwtTokenOf;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
