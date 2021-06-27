package com.careerzip.security.admin;

import com.careerzip.security.oauth.handler.CustomAuthenticationEntryPoint;
import com.careerzip.security.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
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
import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@Profile(value = {"prod", "dev"})
@Configuration
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final FormLoginService formLoginService;
    private final AdminSecurityProperties securityProperties;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
        web.ignoring().antMatchers("/favicon.ico", "/resources/**", "/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            .csrf().disable()
            .headers().frameOptions().disable();

        http.cors(withDefaults());

        http.exceptionHandling()
            .authenticationEntryPoint(customAuthenticationEntryPoint);

        http.antMatcher("/admin/**")
            .authorizeRequests()
            .antMatchers("/admin/login").permitAll()
            // 원활한 GetResponse API 연동 테스트를 위해 잠시 권한을 오픈 합니다.
            .antMatchers("/admin/news-letter/campaigns").permitAll()
            .antMatchers("/admin/**").hasRole(ADMIN.name())
            .anyRequest().authenticated();

        http.formLogin()
            .loginPage("/admin/login")
            .loginProcessingUrl("/admin/login")
            .defaultSuccessUrl(securityProperties.getDefaultSuccessUrl())
            .failureUrl(securityProperties.getLoginFailureUrl())
            .usernameParameter(securityProperties.getUsernameParameter())
            .passwordParameter(securityProperties.getPasswordParameter());

        http.rememberMe()
            .userDetailsService(formLoginService)
            .key(securityProperties.getRememberMeKey())
            .rememberMeParameter(securityProperties.getRememberMeParameter())
            .tokenValiditySeconds(Integer.parseInt(securityProperties.getCookieExpiration()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
