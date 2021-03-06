package com.careerzip.security.oauth.dto;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.entity.Provider;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Getter
public class OAuthAccount implements OAuth2User {

    private final Map<String, Object> attributes;
    private final Set<SimpleGrantedAuthority> authorities;
    private final Long id;
    private final Provider provider;
    private final String oAuthId;
    private final String oAuthEmail;
    private final String name;
    private final String email;
    private final String avatarUrl;

    @Builder
    private OAuthAccount(Map<String, Object> attributes, Set<SimpleGrantedAuthority> authorities, Long id, Provider provider,
                         String oAuthId, String oAuthEmail, String name, String email, String avatarUrl) {
        this.attributes = attributes;
        this.authorities = authorities;
        this.id = id;
        this.provider = provider;
        this.oAuthId = oAuthId;
        this.oAuthEmail = oAuthEmail;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    public static OAuthAccount of(Map<String, Object> attributes, Account account) {
        return OAuthAccount.builder()
                           .attributes(attributes)
                           .authorities(Collections.singleton(new SimpleGrantedAuthority(account.getRoleValue())))
                           .id(account.getId())
                           .oAuthId(account.getOAuthId())
                           .oAuthEmail(account.getOAuthEmail())
                           .provider(account.getProvider())
                           .name(account.getName())
                           .email(account.getEmail())
                           .avatarUrl(account.getAvatarUrl())
                           .build();
    }

    // TODO: ?????? ?????? ??????
    public static OAuthAccount from(Account account) {
        return OAuthAccount.builder()
                           .attributes(Collections.emptyMap())
                           .authorities(Collections.singleton(new SimpleGrantedAuthority(account.getRoleValue())))
                           .id(account.getId())
                           .oAuthId(account.getOAuthId())
                           .oAuthEmail(account.getOAuthEmail())
                           .provider(account.getProvider())
                           .name(account.getName())
                           .email(account.getEmail())
                           .avatarUrl(account.getAvatarUrl())
                           .build();
    }
}
