package com.careerzip.security.admin;

import com.careerzip.security.oauth.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.careerzip.domain.account.entity.Role.ADMIN;

@RequiredArgsConstructor
@Profile(value = {"prod", "dev"} )
@Configuration
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final AdminSecurityProperties securityProperties;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET, "/admin/**")
                      .antMatchers(HttpMethod.POST, "/admin/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            .csrf().disable()
            .headers().frameOptions().disable();

        http.exceptionHandling()
            .authenticationEntryPoint(customAuthenticationEntryPoint);

        http.antMatcher("/admin/**")
            .authorizeRequests()
            .antMatchers("/admin/archives").hasRole(ADMIN.name());

        http.formLogin()
            .loginProcessingUrl("/admin/login")
            .defaultSuccessUrl(securityProperties.getDefaultSuccessUrl())
            .failureUrl(securityProperties.getLoginFailureUrl())
            .usernameParameter(securityProperties.getUsernameParameter())
            .passwordParameter(securityProperties.getPasswordParameter());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
