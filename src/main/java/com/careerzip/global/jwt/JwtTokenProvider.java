package com.careerzip.global.jwt;

import com.careerzip.account.dto.response.AccountSummary;
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

    public boolean validateAuthorizationToken(String header) {
        if (isInvalidHeader(header)) {
            throw new InvalidJwtTokenException();
        }

        String token = extractToken(header);

        try {
            Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parse(token);
        } catch (ExpiredJwtException exception) {
            throw new JwtExpirationException();
        } catch (JwtException exception) {
            throw new InvalidJwtTokenException();
        }

        return true;
    }

    private boolean isInvalidHeader(String header) {
        return !header.startsWith("Bearer ");
    }

    private String extractToken(String header) {
        return header.substring("Bearer ".length());
    }
}
