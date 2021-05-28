package com.careerzip.security.filter;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.global.error.exception.EntityNotFoundException;
import com.careerzip.global.error.exception.JwtValidationException;
import com.careerzip.global.error.exception.entity.AccountNotFoundException;
import com.careerzip.global.error.response.ErrorCode;
import com.careerzip.global.error.response.ErrorResponse;
import com.careerzip.global.jwt.JwtTokenProvider;
import com.careerzip.global.jwt.claims.AccountClaims;
import com.careerzip.security.oauth.dto.OAuthAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Getter
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;

    @Builder
    private JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider, AccountRepository accountRepository,
                                   ObjectMapper objectMapper) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.accountRepository = accountRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            AccountClaims accountClaims = jwtTokenProvider.parseJwtToken(authorizationHeader);

            Account account = accountRepository.findById(accountClaims.getId()).orElseThrow(AccountNotFoundException::new);
            OAuthAccount loginAccount = OAuthAccount.from(account);

            Authentication authentication = new UsernamePasswordAuthenticationToken(loginAccount, jwtTokenProvider.extractToken(authorizationHeader),
                                                                                    loginAccount.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (JwtValidationException exception) {
            // TODO: 에러 처리 중복 로직 정리
            log.error("handleJwtValidationException", exception);
            ErrorCode errorCode = exception.getErrorCode();
            String errorResponse = objectMapper.writeValueAsString(ErrorResponse.from(errorCode));
            response.getWriter().print(errorResponse);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(errorCode.getStatusCode());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (EntityNotFoundException exception) {
            log.error("handleEntityNotFoundException", exception);
            ErrorCode errorCode = exception.getErrorCode();
            String errorResponse = objectMapper.writeValueAsString(ErrorResponse.from(errorCode));
            response.getWriter().print(errorResponse);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(errorCode.getStatusCode());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception exception) {
            log.error("Exception", exception);
            ErrorCode errorCode =  ErrorCode.INTERNAL_SERVER_ERROR;
            String errorResponse = objectMapper.writeValueAsString(ErrorResponse.from(errorCode));
            response.getWriter().print(errorResponse);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(errorCode.getStatusCode());
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    public static JwtAuthorizationFilter of(JwtTokenProvider jwtTokenProvider, AccountRepository accountRepository,
                                            ObjectMapper objectMapper) {
        return JwtAuthorizationFilter.builder()
                                     .jwtTokenProvider(jwtTokenProvider)
                                     .accountRepository(accountRepository)
                                     .objectMapper(objectMapper)
                                     .build();
    }
}
