package com.careerzip.config.web;

import com.careerzip.security.admin.AdminSecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import java.util.Collections;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AdminSecurityProperties securityProperties;
    private static final String CLIENT_URL = "https://career-zip.com";
    private static final String DEV_URL = "https://dev.career-zip.com";
    private static final String CLIENT_LOCAL = "http://localhost:3000";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(CLIENT_URL, DEV_URL, CLIENT_LOCAL, securityProperties.getBaseUrl())
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.SET_COOKIE);
    }

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));
            SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
            sessionCookieConfig.setHttpOnly(false);
            sessionCookieConfig.setSecure(false);
        };
    }
}
