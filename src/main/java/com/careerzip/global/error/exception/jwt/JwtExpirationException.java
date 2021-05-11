package com.careerzip.global.error.exception.jwt;

import com.careerzip.global.error.exception.JwtValidationException;
import com.careerzip.global.error.response.ErrorCode;

public class JwtExpirationException extends JwtValidationException {

    public JwtExpirationException() {
        super(ErrorCode.JWT_EXPIRED_ERROR);
    }
}
