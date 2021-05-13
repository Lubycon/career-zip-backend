package com.careerzip.domain.account.entity;

import com.careerzip.global.error.exception.auth.InvalidOAuthProviderException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum Provider {
    GOOGLE("sub", "name", "email", "picture"),
    NAVER("id", "name", "email", "profile_image")
    ;

    private final String idKey;
    private final String nameKey;
    private final String emailKey;
    private final String avatarUrlKey;

    Provider(String idKey, String nameKey, String emailKey, String avatarUrlKey) {
        this.idKey = idKey;
        this.nameKey = nameKey;
        this.emailKey = emailKey;
        this.avatarUrlKey = avatarUrlKey;
    }

    public static boolean isInvalidProvider(String providerName) {
        String upperCasedProvider = providerName.toUpperCase();
        return !StringUtils.containsAny(upperCasedProvider, GOOGLE.name(), NAVER.name());
    }

    public static Provider mapToValue(String providerName) {
        if (isInvalidProvider(providerName)) {
            throw new InvalidOAuthProviderException();
        }

        return valueOf(providerName.toUpperCase());
    }
}
