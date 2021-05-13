package com.careerzip.global.error.response;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Auth
    INVALID_OAUTH_PROVIDER_ERROR(400, "올바른 인증 요청이 아닙니다."),
    UNAUTHORIZED_ERROR(401, "인증이 필요합니다. 로그인을 해주세요."),

    // JWT
    JWT_INVALIDATION_ERROR(400, "유효한 인증 정보가 아닙니다."),
    JWT_EXPIRED_ERROR(419, "인증 정보가 만료 되었습니다. 다시 로그인 해주세요."),

    // Account
    ACCOUNT_NOT_FOUND(404, "해당 계정을 찾을 수 없습니다.")
    ;

    private final int statusCode;
    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
