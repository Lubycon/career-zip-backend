package com.careerzip.testobject;

import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;

public class QuestionPaperFormFactory {

    public static QuestionPaperForm createJpaQuestionPaperForm() {
        return QuestionPaperForm.builder()
                                .title("Question Paper Form Title")
                                .build();
    }
}
