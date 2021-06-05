package com.careerzip.security;

import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.global.jwt.JwtTokenProvider;
import com.careerzip.security.filter.JwtAuthorizationFilter;
import com.careerzip.security.oauth.handler.CustomAuthenticationEntryPoint;
import com.careerzip.security.oauth.handler.CustomAuthenticationSuccessHandler;
import com.careerzip.security.oauth.service.CustomOAuth2UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;
import java.util.Collections;

import static com.careerzip.domain.account.entity.Role.MEMBER;
import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/v1/accounts/auth")
                      .antMatchers(HttpMethod.GET, "/v1/events/share-link")
                      .antMatchers(HttpMethod.POST, "/v1/events/share-link");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // NOTICE: 개발의 편의성을 위해 csrf 옵션을 종료 합니다.
        http.csrf().disable()
            .httpBasic().disable();

        http.authorizeRequests()
            .antMatchers(HttpMethod.PUT, "/v1/accounts/{id}").hasRole(MEMBER.name())
            .antMatchers(HttpMethod.GET, "/v1/archives").hasRole(MEMBER.name())
            .antMatchers(HttpMethod.POST, "/v1/archives").hasRole(MEMBER.name())
            .antMatchers(HttpMethod.GET, "/v1/archives/{archiveId}").hasRole(MEMBER.name())
            .antMatchers(HttpMethod.POST, "/v1/answers/previous").hasRole(MEMBER.name())
            .antMatchers(HttpMethod.GET, "/v1/questionpapers").hasRole(MEMBER.name())
            .antMatchers(HttpMethod.GET, "/v1/projects").hasRole(MEMBER.name())
            .anyRequest().authenticated();

        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.oauth2Login()
            .userInfoEndpoint()
            .userService(customOAuth2UserService)
                .and()
            .successHandler(customAuthenticationSuccessHandler);

        http.cors(withDefaults());

        http.addFilterBefore(characterEncodingFilter(), CsrfFilter.class)
            .addFilterBefore(JwtAuthorizationFilter.of(jwtTokenProvider, accountRepository, objectMapper), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
            .authenticationEntryPoint(customAuthenticationEntryPoint);
    }

    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return encodingFilter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://career-zip.com", "http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Collections.singletonList(HttpHeaders.AUTHORIZATION));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
