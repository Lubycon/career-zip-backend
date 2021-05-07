package com.careerzip.global.error.jwt;

public class JwtValidationException extends RuntimeException {

    public JwtValidationException() {
        // TODO: 커스텀 예외 상세히 정의
        super("JWT Parsing Faield");
    }
}
