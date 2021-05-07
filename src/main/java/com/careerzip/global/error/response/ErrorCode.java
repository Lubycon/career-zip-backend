package com.careerzip.global.error.response;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Auth
    JWT_INVALIDATION_ERROR(400, "유효한 인증 정보가 아닙니다."),
    JWT_EXPIRED_ERROR(419, "인증 정보가 만료 되었습니다. 다시 로그인 해주세요.")
    ;

    private final int statusCode;
    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
