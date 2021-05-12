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
    private final String attributeKey;

    @NotNull
    private final String oAuthId;

    @NotNull
    private final String name;

    @NotNull
    private final String email;

    @Nullable
    private final String avatarUrl;

    @Builder
    private OAuthAttributes(Map<String, Object> attributes, Provider provider, String attributeKey, String oAuthId, String name,
                            String email, String avatarUrl) {
        this.attributes = attributes;
        this.provider = provider;
        this.attributeKey = attributeKey;
        this.oAuthId = oAuthId;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    public static OAuthAttributes of(Provider provider, Map<String, Object> attributes, String attributeKey) {
        if (provider == Provider.GOOGLE) {
            return ofGoogle(provider, attributes, attributeKey);
        }

        return ofNaver(provider, attributes, attributeKey);
    }

    private static OAuthAttributes ofGoogle(Provider provider, Map<String, Object> attributes, String attributeKey) {
        return OAuthAttributes.builder()
                              .attributes(attributes)
                              .provider(provider)
                              .attributeKey(attributeKey)
                              .oAuthId((String) attributes.get(provider.getIdKey()))
                              .name((String) attributes.get(provider.getNameKey()))
                              .email((String) attributes.get(provider.getEmailKey()))
                              .avatarUrl((String) attributes.get(provider.getAvatarUrlKey()))
                              .build();
    }

    private static OAuthAttributes ofNaver(Provider provider, Map<String, Object> attributes, String attributeKey) {
        Map<String, Object> naverAttributes = (Map<String, Object>) attributes.get(attributeKey);

        return OAuthAttributes.builder()
                              .attributes(naverAttributes)
                              .provider(provider)
                              .attributeKey(provider.getIdKey())
                              .oAuthId((String) naverAttributes.get(provider.getIdKey()))
                              .name((String) naverAttributes.get(provider.getNameKey()))
                              .email((String) naverAttributes.get(provider.getEmailKey()))
                              .avatarUrl((String) naverAttributes.get(provider.getAvatarUrlKey()))
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
