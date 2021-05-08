package com.careerzip.domain.account.dto.response;

import com.careerzip.domain.account.entity.Account;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountSummary {

    private final long id;
    private final String name;
    private final String email;
    private final String avatarUrl;
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
