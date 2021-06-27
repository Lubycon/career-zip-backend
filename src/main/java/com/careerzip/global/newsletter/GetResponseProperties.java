package com.careerzip.global.newsletter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@RequiredArgsConstructor
@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "getresponse")
public class GetResponseProperties {

    private final String baseUrl;
    private final String apiKey;
    private final String authHeaderName;
    private final String mainCampaignId;
}
