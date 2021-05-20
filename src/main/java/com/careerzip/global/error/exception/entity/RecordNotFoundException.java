package com.careerzip.global.error.exception.entity;

import com.careerzip.global.error.exception.EntityNotFoundException;
import com.careerzip.global.error.response.ErrorCode;

public class RecordNotFoundException extends EntityNotFoundException {

    public RecordNotFoundException() {
        super(ErrorCode.RECORD_NOT_FOUND);
    }
}
