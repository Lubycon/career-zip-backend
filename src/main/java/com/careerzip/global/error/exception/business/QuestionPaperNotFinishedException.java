package com.careerzip.global.error.exception.business;

import com.careerzip.global.error.exception.BusinessException;
import com.careerzip.global.error.response.ErrorCode;
import lombok.Getter;

@Getter
public class QuestionPaperNotFinishedException extends BusinessException {

    public QuestionPaperNotFinishedException() {
        super(ErrorCode.QUESTION_PAPER_NOT_FINISHED);
    }
}
