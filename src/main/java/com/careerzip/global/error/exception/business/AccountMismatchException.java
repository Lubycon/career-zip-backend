package com.careerzip.global.error.exception.business;

import com.careerzip.global.error.exception.BusinessException;
import com.careerzip.global.error.response.ErrorCode;

public class AccountMismatchException extends BusinessException {

    public AccountMismatchException() {
        super(ErrorCode.ACCOUNT_MISMATCH_EXCEPTION);
    }
}
