package com.careerzip.global.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@RequiredArgsConstructor
@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private final String issuer;
    private final String secretKey;
    private final String tokenExpiration;
    private final String preAuthTokenExpiration;
    private final String tokenPrefix;
    private final String redirectUrl;
}
