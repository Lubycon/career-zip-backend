package com.careerzip.global.error.exception.jwt;

import com.careerzip.global.error.exception.JwtValidationException;
import com.careerzip.global.error.response.ErrorCode;

public class InvalidJwtTokenException extends JwtValidationException {

    public InvalidJwtTokenException() {
        super(ErrorCode.JWT_INVALIDATION_ERROR);
    }
}
