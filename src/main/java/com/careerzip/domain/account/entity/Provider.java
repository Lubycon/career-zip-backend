package com.careerzip.domain.account.entity;

import org.apache.commons.lang3.StringUtils;

public enum Provider {
    GOOGLE,
    NAVER
    ;

    public static boolean isInvalidProvider(String providerName) {
        String upperCasedProvider = providerName.toUpperCase();
        return !StringUtils.containsAny(upperCasedProvider, GOOGLE.name(), NAVER.name());
    }
}
