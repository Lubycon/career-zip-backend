package com.careerzip.domain.account.dto.response;

import com.careerzip.domain.account.entity.Account;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
public class AccountSummary {

    @NotNull
    private final long id;

    @NotNull
    private final String name;

    @NotNull
    private final String email;

    @Nullable
    private final String avatarUrl;

    @NotNull
    private final String role;

    @Builder
    private AccountSummary(long id, String name, String email, String avatarUrl, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.role = role;
    }

    public static AccountSummary from(Account account) {
        return AccountSummary.builder()
                             .id(account.getId())
                             .name(account.getName())
                             .email(account.getEmail())
                             .avatarUrl(account.getAvatarUrl())
                             .role(account.getRole().name())
                             .build();
    }
}
