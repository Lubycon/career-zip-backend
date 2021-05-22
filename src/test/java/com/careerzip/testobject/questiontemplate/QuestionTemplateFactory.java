package com.careerzip.testobject.questiontemplate;

import com.careerzip.domain.answeroption.AnswerOption;
import com.careerzip.domain.questiontemplate.entity.QuestionTemplate;
import com.careerzip.domain.questiontemplate.entity.QuestionType;

import java.util.Arrays;
import java.util.List;

public class QuestionTemplateFactory {

    // Question - CHECKBOX
    public static QuestionTemplate createCheckboxQuestionTemplate() {
        QuestionTemplate questionTemplate = QuestionTemplate.builder()
                                                            .id(1L)
                                                            .description("Question Description")
                                                            .questionType(QuestionType.CHECKBOX)
                                                            .build();

        AnswerOption firstOption = AnswerOption.builder()
                                               .id(1L)
                                               .description("Option 1")
                                               .questionTemplate(questionTemplate)
                                               .build();
        AnswerOption secondOption = AnswerOption.builder()
                                                .id(2L)
                                                .description("Option 2")
                                                .questionTemplate(questionTemplate)
                                                .build();
        AnswerOption thirdOption = AnswerOption.builder()
                                               .id(3L)
                                               .description("Option 3")
                                               .questionTemplate(questionTemplate)
                                               .build();
        List<AnswerOption> answerOptions = Arrays.asList(firstOption, secondOption, thirdOption);
        questionTemplate.getAnswerOptions().addAll(answerOptions);

        return questionTemplate;
    }

    // Question - TEXT
    public static QuestionTemplate createJpaQuestionTemplate() {
        return QuestionTemplate.builder()
                       .description("Question Description")
                       .example("Question Example")
                       .questionType(QuestionType.TEXT)
                       .build();
    }
}
