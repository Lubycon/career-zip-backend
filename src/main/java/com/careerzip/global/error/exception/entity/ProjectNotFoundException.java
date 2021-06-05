package com.careerzip.global.error.exception.entity;

import com.careerzip.global.error.exception.EntityNotFoundException;
import com.careerzip.global.error.response.ErrorCode;

public class ProjectNotFoundException extends EntityNotFoundException {

    public ProjectNotFoundException() {
        super(ErrorCode.PROJECT_NOT_FOUND);
    }
}
