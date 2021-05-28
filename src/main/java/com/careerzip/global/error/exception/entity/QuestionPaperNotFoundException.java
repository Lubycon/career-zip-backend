package com.careerzip.global.error.exception.entity;

import com.careerzip.global.error.exception.EntityNotFoundException;
import com.careerzip.global.error.response.ErrorCode;

public class QuestionPaperNotFoundException extends EntityNotFoundException {

    public QuestionPaperNotFoundException() {
        super(ErrorCode.QUESTION_PAPER_NOT_FOUND);
    }
}
