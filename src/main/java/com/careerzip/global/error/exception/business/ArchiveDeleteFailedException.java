package com.careerzip.global.error.exception.business;

import com.careerzip.global.error.exception.BusinessException;
import com.careerzip.global.error.response.ErrorCode;
import lombok.Getter;

@Getter
public class ArchiveDeleteFailedException extends BusinessException {

    public ArchiveDeleteFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
