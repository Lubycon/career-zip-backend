package com.careerzip.config.web;

import com.careerzip.security.admin.AdminSecurityProperties;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Context;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
    public ServletWebServerFactory servletContainer() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                Rfc6265CookieProcessor rfc6265CookieProcessor = new Rfc6265CookieProcessor();
                rfc6265CookieProcessor.setSameSiteCookies("None");
                context.setCookieProcessor(rfc6265CookieProcessor);
            }
        };
    }
}
