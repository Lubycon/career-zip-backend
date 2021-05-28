package com.careerzip.global.error.exception.entity;

import com.careerzip.global.error.exception.EntityNotFoundException;
import com.careerzip.global.error.response.ErrorCode;

public class ArchiveNotFoundException extends EntityNotFoundException {

    public ArchiveNotFoundException() {
        super(ErrorCode.RECORD_NOT_FOUND);
    }
}
