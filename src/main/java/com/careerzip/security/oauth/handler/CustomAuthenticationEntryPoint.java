package com.careerzip.security.oauth.handler;

import com.careerzip.global.error.response.ErrorCode;
import com.careerzip.global.error.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.error("handleAuthenticationException", authException);
        ErrorCode unAuthorizedError = ErrorCode.UNAUTHORIZED_ERROR;
        String errorResponse = objectMapper.writeValueAsString(ErrorResponse.from(unAuthorizedError));
        response.getWriter().print(errorResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(unAuthorizedError.getStatusCode());
        response.getWriter().flush();
        response.getWriter().close();
    }
}
