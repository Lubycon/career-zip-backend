package com.careerzip.security.oauth.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.security.oauth.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String provider = clientRegistration.getRegistrationId();
        String userNameAttributeName = clientRegistration.getProviderDetails()
                                                         .getUserInfoEndpoint()
                                                         .getUserNameAttributeName();


        OAuthAttributes attributes = OAuthAttributes.of(provider, oAuth2User.getAttributes(), userNameAttributeName);
        Account account = findOrElseCreate(attributes);
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(account.getRoleValue())),
                                     attributes.getAttributes(), attributes.getOAuthIdKey());
    }

    private Account findOrElseCreate(OAuthAttributes attributes) {
        Account account = accountRepository.findByOAuth(attributes.getProvider(), attributes.getOAuthId())
                                           .orElseGet(attributes::toEntity);
        return accountRepository.save(account);
    }
}
