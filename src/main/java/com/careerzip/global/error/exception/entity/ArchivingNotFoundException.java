package com.careerzip.global.error.exception.entity;

import com.careerzip.global.error.exception.EntityNotFoundException;
import com.careerzip.global.error.response.ErrorCode;

public class ArchivingNotFoundException extends EntityNotFoundException {

    public ArchivingNotFoundException() {
        super(ErrorCode.RECORD_NOT_FOUND);
    }
}
