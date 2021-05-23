package com.careerzip.testobject.question;

import com.careerzip.domain.answeroption.AnswerOption;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.entity.QuestionType;

import java.util.Arrays;
import java.util.List;

public class QuestionFactory {

    // Question - CHECKBOX
    public static Question createCheckboxQuestionTemplate() {
        Question question = Question.builder()
                                    .id(1L)
                                    .description("Question Description")
                                    .questionType(QuestionType.CHECKBOX)
                                    .build();

        AnswerOption firstOption = AnswerOption.builder()
                                               .id(1L)
                                               .description("Option 1")
                                               .question(question)
                                               .build();
        AnswerOption secondOption = AnswerOption.builder()
                                                .id(2L)
                                                .description("Option 2")
                                                .question(question)
                                                .build();
        AnswerOption thirdOption = AnswerOption.builder()
                                               .id(3L)
                                               .description("Option 3")
                                               .question(question)
                                               .build();
        List<AnswerOption> answerOptions = Arrays.asList(firstOption, secondOption, thirdOption);
        question.getAnswerOptions().addAll(answerOptions);

        return question;
    }

    // Question - TEXT
    public static Question createJpaTestTextQuestion() {
        return Question.builder()
                       .description("Question Description")
                       .example("Question Example")
                       .questionType(QuestionType.TEXT)
                       .build();
    }
}
