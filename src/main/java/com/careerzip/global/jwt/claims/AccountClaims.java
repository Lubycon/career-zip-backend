package com.careerzip.global.jwt.claims;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class AccountClaims {

    @NotNull
    private final Long id;

    @NotNull
    private final String email;

    @Builder
    private AccountClaims(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public static AccountClaims from(Claims claims) {
        return AccountClaims.builder()
                            .id(claims.get("id", Long.class))
                            .email(claims.get("email", String.class))
                            .build();
    }
}
