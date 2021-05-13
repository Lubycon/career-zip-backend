package com.careerzip.security;

import com.careerzip.security.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    // OAuth 테스트를 위한 React App redirect URL
    private final String redirectUrl = "http://localhost:3000";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // NOTICE: 개발의 편의성을 위해 csrf 옵션을 종료 합니다.
        http.csrf().disable()
            .httpBasic().disable();

        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/v1/accounts/auth").permitAll()
            .anyRequest().authenticated();

        http.oauth2Login()
            .authorizationEndpoint()
                .and()
            .userInfoEndpoint()
            .userService(customOAuth2UserService)
                .and()
            .defaultSuccessUrl(redirectUrl, true);
        ;
    }
}
