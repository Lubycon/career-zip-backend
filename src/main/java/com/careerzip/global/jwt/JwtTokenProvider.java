package com.careerzip.global.jwt;

import com.careerzip.domain.account.dto.response.AccountSummary;
import com.careerzip.global.error.exception.jwt.InvalidJwtTokenException;
import com.careerzip.global.error.exception.jwt.JwtExpirationException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    public String issueToken(AccountSummary account) {
        Date now = new Date();

        return Jwts.builder()
                   .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                   .setIssuer(jwtProperties.getIssuer())
                   .setIssuedAt(now)
                   .setExpiration(new Date(now.getTime() + Long.parseLong(jwtProperties.getExpiration())))
                   .claim("id", account.getId())
                   .claim("email", account.getEmail())
                   .claim("role", account.getRole())
                   .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                   .compact();
    }

    public Cookie mapTokenToCookie(AccountSummary account) {
        return new Cookie(jwtProperties.getCookieName(), issueToken(account));
    }

    public void validateAuthorizationToken(String authorizationHeader) {
        validateAuthorizationHeader(authorizationHeader);
        String token = extractToken(authorizationHeader);

        try {
            Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parse(token);
        } catch (ExpiredJwtException exception) {
            throw new JwtExpirationException();
        } catch (JwtException exception) {
            throw new InvalidJwtTokenException();
        }
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }

    private void validateAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader.startsWith("Bearer ")) {
            return;
        }

        throw new InvalidJwtTokenException();
    }
}
