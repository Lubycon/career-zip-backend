package com.careerzip.global.error.response;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_ERROR(400, "입력한 요청 값이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(500, "서버에서 요청을 처리하지 못했습니다. 다시 시도 해주세요."),

    // Auth
    INVALID_OAUTH_PROVIDER_ERROR(400, "올바른 인증 요청이 아닙니다."),
    UNAUTHORIZED_ERROR(401, "인증이 필요합니다. 로그인을 해주세요."),

    // JWT
    JWT_INVALIDATION_ERROR(400, "유효한 인증 정보가 아닙니다."),
    JWT_REQUIRED_ERROR(401, "인증이 필요합니다. 로그인을 해주세요."),
    JWT_EXPIRED_ERROR(419, "인증 정보가 만료 되었습니다. 다시 로그인 해주세요."),

    // Account
    ACCOUNT_MISMATCH_EXCEPTION(403, "계정 정보가 일치하지 않습니다. 인증 이후 다시 시도 해주세요."),
    ACCOUNT_NOT_FOUND(404, "해당 계정을 찾을 수 없습니다."),

    // Record
    RECORD_NOT_FOUND(404, "해당 아카이빙 기록을 찾을 수 없습니다."),

    // QuestionPaper
    QUESTION_PAPER_NOT_FOUND(404, "해당 질문지를 찾을 수 없습니다.")
    ;

    private final int statusCode;
    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
