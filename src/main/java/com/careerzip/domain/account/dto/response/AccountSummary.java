package com.careerzip.domain.account.dto.response;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.entity.Role;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class AccountSummary {

    private final long id;

    @NotNull
    private final String name;

    @NotNull
    private final String email;

    @Nullable
    private final String avatarUrl;

    @NotNull
    private final Role role;

    @Builder
    private AccountSummary(long id, String name, String email, String avatarUrl, Role role) {
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
                             .role(account.getRole())
                             .build();
    }
}
