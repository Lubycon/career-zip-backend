package com.careerzip.testobject.questionpaper;

import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;

import java.time.LocalDateTime;

import static com.careerzip.testobject.questionpaperform.QuestionPaperFormFactory.createQuestionPaperForm;

public class QuestionPaperFactory {

    // Question Paper
    public static QuestionPaper createQuestionPaper() {
        return QuestionPaper.builder()
                            .id(1L)
                            .title("Paper Title")
                            .startDateTime(LocalDateTime.of(2021, 05, 20, 5, 2, 1))
                            .endDateTime(LocalDateTime.of(2021, 05, 20, 7, 2, 1))
                            .opened(true)
                            .questionPaperForm(createQuestionPaperForm())
                            .build();
    }

    public static QuestionPaper createJpaQuestionPaperOf(QuestionPaperForm questionPaperForm) {
        return QuestionPaper.builder()
                            .title("Paper Title")
                            .startDateTime(LocalDateTime.of(2021, 05, 20, 5, 2, 1))
                            .endDateTime(LocalDateTime.of(2021, 05, 20, 7, 2, 1))
                            .opened(true)
                            .questionPaperForm(questionPaperForm)
                            .build();
    }
}
