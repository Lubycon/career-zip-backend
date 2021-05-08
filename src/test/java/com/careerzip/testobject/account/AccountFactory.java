package com.careerzip.testobject.account;

import com.careerzip.account.dto.request.AccountRequest;
import com.careerzip.account.dto.request.AccountRequestBuilder;
import com.careerzip.account.dto.response.AccountSummary;
import com.careerzip.account.entity.Account;
import com.careerzip.account.entity.Provider;
import com.careerzip.account.entity.Role;

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
                      .oauthId("OAuth ID")
                      .provider(Provider.GOOGLE)
                      .name("Account Name")
                      .email("Email")
                      .avatarUrl("https://avatarUrl")
                      .role(Role.MEMBER)
                      .build();
    }

    public static Account createMemberOf(Long id) {
        return Account.builder()
                      .id(id)
                      .oauthId("OAuth ID")
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
                      .oauthId(accountRequest.getOAuthId())
                      .provider(Provider.valueOf(accountRequest.getProvider()))
                      .name(accountRequest.getName())
                      .email(accountRequest.getEmail())
                      .avatarUrl(accountRequest.getAvatarUrl())
                      .role(Role.MEMBER)
                      .build();
    }

    // AccountRequest
    public static AccountRequest createAccountRequest() {
        return AccountRequestBuilder.newBuilder()
                                    .provider("GOOGLE")
                                    .oAuthId("OAuthID")
                                    .name("Username")
                                    .email("account@email.com")
                                    .avatarUrl("https://avatarUrl")
                                    .build();
    }

    public static AccountRequest createAccountRequestOf(String provider) {
        return AccountRequestBuilder.newBuilder()
                                    .provider(provider)
                                    .oAuthId("OAuthID")
                                    .name("Username")
                                    .email("account@email.com")
                                    .avatarUrl("https://avatarUrl")
                                    .build();
    }

    // AccountSummaryResponse
    public static AccountSummary createAccountSummaryResponse() {
        return AccountSummary.builder()
                             .id(1L)
                             .name("Username")
                             .email("account@email.com")
                             .avatarUrl("https://avatarUrl")
                             .role("MEMBER")
                             .build();
    }
}
