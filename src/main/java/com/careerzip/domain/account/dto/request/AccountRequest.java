package com.careerzip.domain.account.dto.request;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.entity.Provider;
import com.careerzip.domain.account.entity.Role;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public Account toEntity() {
        return Account.builder()
                      .oAuthId(oAuthId)
                      .provider(Provider.valueOf(provider))
                      .name(name)
                      .email(email)
                      .avatarUrl(avatarUrl)
                      .role(Role.MEMBER)
                      .submitCount(0)
                      .deleted(false)
                      .build();
    }
}
