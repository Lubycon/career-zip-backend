package com.careerzip.security.filter;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.global.error.exception.entity.AccountNotFoundException;
import com.careerzip.global.jwt.JwtTokenProvider;
import com.careerzip.global.jwt.claims.AccountClaims;
import com.careerzip.security.oauth.dto.OAuthAccount;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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

    private JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider, AccountRepository accountRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.accountRepository = accountRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = response.getHeader(HttpHeaders.AUTHORIZATION);
        AccountClaims accountClaims = jwtTokenProvider.parseJwtToken(authorizationHeader);

        Account account = accountRepository.findById(accountClaims.getId()).orElseThrow(AccountNotFoundException::new);
        OAuthAccount loginAccount = OAuthAccount.from(account);

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginAccount, jwtTokenProvider.extractToken(authorizationHeader),
                                                                                loginAccount.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    public static JwtAuthorizationFilter of(JwtTokenProvider jwtTokenProvider, AccountRepository accountRepository) {
        return new JwtAuthorizationFilter(jwtTokenProvider, accountRepository);
    }
}
