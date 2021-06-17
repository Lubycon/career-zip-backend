package com.careerzip.global.error.exception.auth;

import com.careerzip.global.error.exception.AuthException;
import com.careerzip.global.error.response.ErrorCode;

public class InvalidOAuthAuthenticationException extends AuthException {

    public InvalidOAuthAuthenticationException() {
        super(ErrorCode.INVALID_OAUTH_AUTHENTICATION_ERROR);
    }
}
