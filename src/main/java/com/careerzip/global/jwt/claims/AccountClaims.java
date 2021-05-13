package com.careerzip.global.jwt.claims;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class AccountClaims {

    @NotNull
    private final Long id;

    @NotNull
    private final String name;

    @NotNull
    private final String email;

    @NotNull
    private final String role;

    @Nullable
    private final String avatarUrl;

    @Builder
    private AccountClaims(Long id, String name, String email, String role, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.avatarUrl = avatarUrl;
    }

    public static AccountClaims from(Claims claims) {
        return AccountClaims.builder()
                            .id(claims.get("id", Long.class))
                            .name(claims.get("name", String.class))
                            .email(claims.get("email", String.class))
                            .role(claims.get("role", String.class))
                            .avatarUrl(claims.get("avatarUrl", String.class))
                            .build();
    }
}
