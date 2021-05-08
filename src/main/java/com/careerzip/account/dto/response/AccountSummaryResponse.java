package com.careerzip.account.dto.response;

import com.careerzip.account.entity.Account;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountSummaryResponse {

    private final long id;
    private final String name;
    private final String email;
    private final String avatarUrl;
    private final String role;

    @Builder
    private AccountSummaryResponse(long id, String name, String email, String avatarUrl, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.role = role;
    }

    public static AccountSummaryResponse from(Account account) {
        return AccountSummaryResponse.builder()
                                     .id(account.getId())
                                     .name(account.getName())
                                     .email(account.getEmail())
                                     .avatarUrl(account.getAvatarUrl())
                                     .role(account.getRole().name())
                                     .build();
    }
}
