package com.careerzip.testobject.question;

import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.questionitem.entity.QuestionItem;
import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;

import java.util.Arrays;
import java.util.List;

import static com.careerzip.testobject.questionpaperform.QuestionPaperFormFactory.createQuestionPaperForm;
import static com.careerzip.testobject.questionitem.QuestionItemFactory.createCheckboxQuestionItem;

public class QuestionFactory {

    // Question
    public static Question createQuestion() {
        return Question.builder()
                       .id(1L)
                       .questionPaperForm(createQuestionPaperForm())
                       .questionItem(createCheckboxQuestionItem())
                       .priority(1)
                       .build();
    }

    public static Question createQuestionOf(QuestionItem questionItem) {
        return Question.builder()
                       .id(1L)
                       .questionPaperForm(createQuestionPaperForm())
                       .questionItem(questionItem)
                       .priority(1)
                       .build();
    }

    public static Question createQuestionOf(Long id, QuestionItem questionItem) {
        return Question.builder()
                       .id(id)
                       .questionPaperForm(createQuestionPaperForm())
                       .questionItem(questionItem)
                       .priority(1)
                       .build();
    }

    public static Question createJpaQuestionOf(QuestionPaperForm questionPaperForm, QuestionItem questionItem) {
            return Question.builder()
                           .questionPaperForm(questionPaperForm)
                           .questionItem(questionItem)
                           .priority(1)
                           .build();
    }

    // Questions
    public static List<Question> createQuestions() {
        return Arrays.asList(Question.builder()
                                     .id(1L)
                                     .questionPaperForm(createQuestionPaperForm())
                                     .priority(1)
                                     .questionItem(createCheckboxQuestionItem())
                                     .build(),
                             Question.builder()
                                     .id(2L)
                                     .priority(2)
                                     .questionItem(createCheckboxQuestionItem())
                                     .questionPaperForm(createQuestionPaperForm())
                                     .build(),
                             Question.builder()
                                     .id(3L)
                                     .priority(3)
                                     .questionItem(createCheckboxQuestionItem())
                                     .questionPaperForm(createQuestionPaperForm())
                                     .build());
    }
}
