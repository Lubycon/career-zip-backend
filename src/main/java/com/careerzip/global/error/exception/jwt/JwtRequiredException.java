package com.careerzip.global.error.exception.jwt;

import com.careerzip.global.error.exception.JwtValidationException;
import com.careerzip.global.error.response.ErrorCode;

public class JwtRequiredException extends JwtValidationException {

    public JwtRequiredException() {
        super(ErrorCode.JWT_REQUIRED_ERROR);
    }
}
