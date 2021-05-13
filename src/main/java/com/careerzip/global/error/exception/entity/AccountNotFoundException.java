package com.careerzip.global.error.exception.entity;

import com.careerzip.global.error.exception.EntityNotFoundException;
import com.careerzip.global.error.response.ErrorCode;

public class AccountNotFoundException extends EntityNotFoundException {

    public AccountNotFoundException() {
        super(ErrorCode.ACCOUNT_NOT_FOUND);
    }
}
