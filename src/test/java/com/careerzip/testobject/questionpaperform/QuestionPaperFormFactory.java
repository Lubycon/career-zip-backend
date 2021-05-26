package com.careerzip.testobject.questionpaperform;

import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;

public class QuestionPaperFormFactory {

    // PaperForm
    public static QuestionPaperForm createQuestionPaperForm() {
        return QuestionPaperForm.builder()
                                .id(1L)
                                .title("Template title")
                                .build();
    }

    public static QuestionPaperForm createJpaTestQuestionPaperForm() {
        return QuestionPaperForm.from("New Template");
    }
}
