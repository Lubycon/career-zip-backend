package com.careerzip.domain.account.dto.request;

import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
public class AccountRequest {

    @NotNull
    private String provider;

    @NotNull
    private String oAuthId;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @Nullable
    private String avatarUrl;

    AccountRequest(String provider, String oAuthId, String name, String email, String avatarUrl) {
        this.provider = provider;
        this.oAuthId = oAuthId;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }
}
