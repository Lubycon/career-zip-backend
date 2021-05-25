package com.careerzip.testobject.questiontype;

import com.careerzip.domain.questiontype.QuestionType;

public class QuestionTypeFactory {

    public static QuestionType createQuestionType() {
        return QuestionType.builder()
                           .id(1L)
                           .description("Question Type")
                           .build();
    }
}
