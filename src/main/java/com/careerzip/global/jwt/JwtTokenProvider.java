package com.careerzip.global.jwt;

import com.careerzip.account.entity.Account;
import com.careerzip.global.error.jwt.JwtValidationException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    public String issueToken(Account account) {
        Date now = new Date();

        return Jwts.builder()
                   .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                   .setIssuer(jwtProperties.getIssuer())
                   .setIssuedAt(now)
                   .setExpiration(new Date(now.getTime() + jwtProperties.getExpiration()))
                   .claim("id", account.getId())
                   .claim("email", account.getEmail())
                   .claim("role", account.getRole().name())
                   .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                   .compact();
    }

    public boolean validateAuthorizationToken(String header) {
        String headerPrefix = "Bearer ";

        if (header.startsWith(headerPrefix)) {
            String token = header.substring(headerPrefix.length());

            try {
                Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parse(token);
            } catch (JwtException exception) {
                // TODO: 커스텀 에러 상세 정의
                throw new JwtValidationException();
            }
        }

        return true;
    }
}
