package com.careerzip.testobject.question;

import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.questionitem.entity.QuestionItem;
import com.careerzip.domain.letterform.entity.LetterForm;

import static com.careerzip.testobject.letterform.TemplateFactory.createLetterFormOf;
import static com.careerzip.testobject.questionitem.QuestionItemFactory.createCheckboxQuestionItem;

public class QuestionFactory {

    // Question
    public static Question createQuestion() {
        return Question.builder()
                       .id(1L)
                       .letterForm(createLetterFormOf())
                       .questionItem(createCheckboxQuestionItem())
                       .priority(1)
                       .build();
    }

    public static Question createQuestionOf(QuestionItem questionItem) {
        return Question.builder()
                       .id(1L)
                       .letterForm(createLetterFormOf())
                       .questionItem(questionItem)
                       .priority(1)
                       .build();
    }

    public static Question createQuestionOf(Long id, QuestionItem questionItem) {
        return Question.builder()
                       .id(id)
                       .letterForm(createLetterFormOf())
                       .questionItem(questionItem)
                       .priority(1)
                       .build();
    }

    public static Question createJpaTestQuestionOf(LetterForm letterForm, QuestionItem questionItem) {
            return Question.builder()
                           .letterForm(letterForm)
                           .questionItem(questionItem)
                           .priority(1)
                           .build();
    }
}
