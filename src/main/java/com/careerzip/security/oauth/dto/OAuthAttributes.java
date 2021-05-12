package com.careerzip.security.oauth.dto;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.entity.Provider;
import com.careerzip.domain.account.entity.Role;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@Getter
public class OAuthAttributes {

    @NotNull
    private final Map<String, Object> attributes;

    @NotNull
    private final Provider provider;

    @NotNull
    private final String oAuthIdKey;

    @NotNull
    private final String oAuthId;

    @NotNull
    private final String name;

    @NotNull
    private final String email;

    @Nullable
    private final String avatarUrl;

    @Builder
    private OAuthAttributes(Map<String, Object> attributes, Provider provider, String oAuthIdKey, String oAuthId, String name,
                            String email, String avatarUrl) {
        this.attributes = attributes;
        this.provider = provider;
        this.oAuthIdKey = oAuthIdKey;
        this.oAuthId = oAuthId;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    public static OAuthAttributes of(String provider, Map<String, Object> attributes, String oAuthIdKey) {
        if (Provider.mapToValue(provider) == Provider.GOOGLE) {
            return ofGoogle(provider, attributes, oAuthIdKey);
        }

        return ofNaver(provider, attributes, oAuthIdKey);
    }

    private static OAuthAttributes ofGoogle(String provider, Map<String, Object> attributes, String oAuthIdKey) {
        return OAuthAttributes.builder()
                              .attributes(attributes)
                              .provider(Provider.mapToValue(provider))
                              .oAuthIdKey(oAuthIdKey)
                              .oAuthId((String) attributes.get(oAuthIdKey))
                              .name((String) attributes.get("name"))
                              .email((String) attributes.get("email"))
                              .avatarUrl((String) attributes.get("picture"))
                              .build();
    }

    private static OAuthAttributes ofNaver(String provider, Map<String, Object> attributes, String oAuthIdKey) {
        Map<String, Object> naverAttributes = (Map<String, Object>) attributes.get(oAuthIdKey);

        return OAuthAttributes.builder()
                              .attributes(naverAttributes)
                              .provider(Provider.mapToValue(provider))
                              .oAuthIdKey("id")
                              .oAuthId((String) naverAttributes.get("id"))
                              .name((String) naverAttributes.get("name"))
                              .email((String) naverAttributes.get("email"))
                              .avatarUrl((String) naverAttributes.get("profile_image"))
                              .build();
    }

    public Account toEntity() {
        return Account.builder()
                      .oAuthId(oAuthId)
                      .provider(provider)
                      .name(name)
                      .email(email)
                      .avatarUrl(avatarUrl)
                      .role(Role.MEMBER)
                      .submitCount(0)
                      .deleted(false)
                      .build();
    }
}
