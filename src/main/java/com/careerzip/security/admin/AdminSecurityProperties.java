package com.careerzip.security.admin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@RequiredArgsConstructor
@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "admin.security")
public class AdminSecurityProperties {

    private final String baseUrl;
    private final String defaultSuccessUrl;
    private final String loginFailureUrl;
    private final String usernameParameter;
    private final String passwordParameter;
}
