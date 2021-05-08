package com.careerzip.account.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AccountRequest {

    private String provider;
    private String oAuthId;
    private String name;
    private String email;
    private String avatarUrl;

    AccountRequest(String provider, String oAuthId, String name, String email, String avatarUrl) {
        this.provider = provider;
        this.oAuthId = oAuthId;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }
}
