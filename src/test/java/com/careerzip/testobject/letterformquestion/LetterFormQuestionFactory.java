package com.careerzip.testobject.letterformquestion;

import com.careerzip.domain.letterformquestion.entity.LetterFormQuestion;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.letterform.entity.LetterForm;

import static com.careerzip.testobject.letterform.TemplateFactory.createLetterFormOf;

public class LetterFormQuestionFactory {

    // LetterFormQuestion
    public static LetterFormQuestion createLetterFormQuestionOf(Question question) {
        return LetterFormQuestion.builder()
                               .id(1L)
                               .letterForm(createLetterFormOf())
                               .question(question)
                               .priority(1)
                               .build();
    }

    public static LetterFormQuestion createJpaTestLetterFormQuestionOf(LetterForm letterForm, Question question) {
            return LetterFormQuestion.builder()
                                   .letterForm(letterForm)
                                   .question(question)
                                   .priority(1)
                                   .build();
    }
}
