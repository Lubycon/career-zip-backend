package com.careerzip.testobject.account;

import com.careerzip.domain.account.dto.request.AccountRequest;
import com.careerzip.domain.account.dto.request.AccountRequestBuilder;
import com.careerzip.domain.account.dto.request.AccountUpdateRequest;
import com.careerzip.domain.account.dto.request.AccountUpdateRequestBuilder;
import com.careerzip.domain.account.dto.response.AccountSummary;
import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.entity.Provider;
import com.careerzip.domain.account.entity.Role;
import com.careerzip.security.oauth.dto.OAuthAccount;
import com.careerzip.security.oauth.dto.OAuthAttributes;

public class AccountFactory {

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

    // Account - MEMBER
    public static Account createMember() {
        return Account.builder()
                      .id(1L)
                      .oAuthId("OAuth ID")
                      .oAuthEmail("OAuth Email")
                      .provider(Provider.GOOGLE)
                      .name("Account Name")
                      .email("Email")
                      .avatarUrl("https://avatarUrl")
                      .role(Role.MEMBER)
                      .build();
    }

    public static Account createMemberOf(AccountRequest accountRequest) {
        return Account.builder()
                      .id(1L)
                      .oAuthId(accountRequest.getOAuthId())
                      .provider(Provider.valueOf(accountRequest.getProvider()))
                      .name(accountRequest.getName())
                      .email(accountRequest.getEmail())
                      .avatarUrl(accountRequest.getAvatarUrl())
                      .role(Role.MEMBER)
                      .build();
    }

    public static Account createIntegrationTestMember() {
        return Account.builder()
                      .oAuthId("OAuth ID")
                      .oAuthEmail("OAuth Email")
                      .provider(Provider.GOOGLE)
                      .name("Account Name")
                      .email("Email")
                      .avatarUrl("https://avatarUrl")
                      .role(Role.MEMBER)
                      .build();
    }

    // OAuthAccount
    public static OAuthAccount createOAuthAccountOf(Account account) {
        return OAuthAccount.of(null, account);
    }

    // AccountUpdateRequest
    public static AccountUpdateRequest createAccountUpdateRequest(String name, String email) {
        return AccountUpdateRequestBuilder.newBuilder()
                                          .name(name)
                                          .email(email)
                                          .build();

    }

    // AccountSummaryResponse
    public static AccountSummary createAccountSummaryResponse() {
        return AccountSummary.builder()
                             .id(1L)
                             .name("Username")
                             .email("account@email.com")
                             .avatarUrl("https://avatarUrl")
                             .role(Role.MEMBER)
                             .build();
    }
}
