package com.careerzip.security;

import com.careerzip.security.oauth.handler.CustomAuthenticationEntryPoint;
import com.careerzip.security.oauth.handler.CustomAuthenticationSuccessHandler;
import com.careerzip.security.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // NOTICE: 개발의 편의성을 위해 csrf 옵션을 종료 합니다.
        http.csrf().disable()
            .httpBasic().disable();

        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/v1/accounts/auth").permitAll()
            .anyRequest().authenticated();

        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.oauth2Login()
            .userInfoEndpoint()
            .userService(customOAuth2UserService)
                .and()
            .successHandler(customAuthenticationSuccessHandler);

        http.exceptionHandling()
            .authenticationEntryPoint(customAuthenticationEntryPoint);
    }
}
