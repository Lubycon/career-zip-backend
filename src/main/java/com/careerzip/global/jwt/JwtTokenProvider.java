package com.careerzip.global.jwt;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.global.error.exception.jwt.InvalidJwtTokenException;
import com.careerzip.global.error.exception.jwt.JwtExpirationException;
import com.careerzip.global.jwt.claims.AccountClaims;
import com.careerzip.security.oauth.dto.OAuthAccount;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    public String issueJwtToken(Account account) {
        Date now = new Date();

        String token = Jwts.builder()
                           .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                           .setIssuer(jwtProperties.getIssuer())
                           .setIssuedAt(now)
                           .setExpiration(new Date(now.getTime() + Long.parseLong(jwtProperties.getTokenExpiration())))
                           .claim("id", account.getId())
                           .claim("name", account.getName())
                           .claim("email", account.getEmail())
                           .claim("avatarUrl", account.getAvatarUrl())
                           .claim("role", account.getRole())
                           .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                           .compact();

        return jwtProperties.getTokenPrefix() + token;
    }

    public String issuePreAuthToken(OAuthAccount account) {
        Date now = new Date();

        return Jwts.builder()
                   .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                   .setIssuer(jwtProperties.getIssuer())
                   .setIssuedAt(now)
                   .setExpiration(new Date(now.getTime() + Long.parseLong(jwtProperties.getPreAuthTokenExpiration())))
                   .claim("id", account.getId())
                   .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                   .compact();
    }

    public AccountClaims parseJwtToken(String authorizationHeader) {
        validateAuthorizationHeader(authorizationHeader);
        String token = extractToken(authorizationHeader);

        try {
            Claims claims = Jwts.parser()
                                .setSigningKey(jwtProperties.getSecretKey())
                                .parseClaimsJws(token)
                                .getBody();
            return AccountClaims.from(claims);
        } catch (ExpiredJwtException exception) {
            throw new JwtExpirationException();
        } catch (JwtException exception) {
            throw new InvalidJwtTokenException();
        }
    }

    public Long parsePreAuthToken(String authorizationHeader) {
        validateAuthorizationHeader(authorizationHeader);
        String token = extractToken(authorizationHeader);

        try {
            return Jwts.parser()
                       .setSigningKey(jwtProperties.getSecretKey())
                       .parseClaimsJws(token)
                       .getBody()
                       .get("id", Long.class);
        } catch (ExpiredJwtException exception) {
            throw new JwtExpirationException();
        } catch (JwtException exception) {
            throw new InvalidJwtTokenException();
        }
    }

    public String createPreAuthTokenUri(OAuthAccount account) {
        return UriComponentsBuilder.fromHttpUrl(jwtProperties.getRedirectUrl())
                                   .queryParam("authorizationToken", issuePreAuthToken(account))
                                   .build()
                                   .toString();
    }

    // TODO: 중복 로직 개선
    public String extractToken(String authorizationHeader) {
        validateAuthorizationHeader(authorizationHeader);
        return authorizationHeader.substring(jwtProperties.getTokenPrefix().length());
    }

    private void validateAuthorizationHeader(String authorizationHeader) {
        if (!authorizationHeader.startsWith(jwtProperties.getTokenPrefix())) {
            throw new InvalidJwtTokenException();
        }
    }
}
