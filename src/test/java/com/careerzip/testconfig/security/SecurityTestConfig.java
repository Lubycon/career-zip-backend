package com.careerzip.testconfig.security;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.careerzip.domain.account.entity.Role.MEMBER;

@TestConfiguration
public class SecurityTestConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v1/accounts/auth");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.addFilterBefore(characterEncodingFilter(), CsrfFilter.class)
            .addFilterBefore(new MockJwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
            .antMatchers(HttpMethod.PUT, "/v1/accounts/{accountId}").hasRole(MEMBER.name())
            .anyRequest().authenticated();
    }

    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return encodingFilter;
    }
}
