package com.careerzip.domain.account.entity;

import com.careerzip.global.error.exception.auth.InvalidOAuthProviderException;
import org.apache.commons.lang3.StringUtils;

public enum Provider {
    GOOGLE,
    NAVER
    ;

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
