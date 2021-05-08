package com.careerzip.global.error.exception.auth;

import com.careerzip.global.error.exception.AuthException;
import com.careerzip.global.error.response.ErrorCode;

public class InvalidOAuthProviderException extends AuthException {

    public InvalidOAuthProviderException() {
        super(ErrorCode.INVALID_OAUTH_PROVIDER_ERROR);
    }
}
