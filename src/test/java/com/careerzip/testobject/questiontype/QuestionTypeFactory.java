package com.careerzip.testobject.questiontype;

import com.careerzip.domain.questiontype.entity.QuestionType;

public class QuestionTypeFactory {

    // QuestionType
    public static QuestionType createQuestionType() {
        return QuestionType.builder()
                           .id(1L)
                           .name("Question Type")
                           .build();
    }

    public static QuestionType createJpaQuestionType() {
        return QuestionType.builder()
                           .name("Question Type")
                           .build();
    }
}
