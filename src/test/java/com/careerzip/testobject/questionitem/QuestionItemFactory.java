package com.careerzip.testobject.questionitem;

import com.careerzip.domain.questionoption.QuestionOption;
import com.careerzip.domain.questionitem.entity.QuestionItem;
import com.careerzip.domain.questionitem.entity.InputType;

import java.util.Arrays;
import java.util.List;

import static com.careerzip.testobject.questiontype.QuestionTypeFactory.createQuestionType;

public class QuestionItemFactory {

    // QuestionItem - CHECKBOX
    public static QuestionItem createCheckboxQuestionItem() {
        QuestionItem questionItem = QuestionItem.builder()
                                                .id(1L)
                                                .description("Question Description")
                                                .inputType(InputType.CHECKBOX)
                                                .questionType(createQuestionType())
                                                .build();

        QuestionOption firstOption = QuestionOption.builder()
                                                    .id(1L)
                                                    .description("Option 1")
                                                    .questionItem(questionItem)
                                                    .build();
        QuestionOption secondOption = QuestionOption.builder()
                                                    .id(2L)
                                                    .description("Option 2")
                                                    .questionItem(questionItem)
                                                    .build();
        QuestionOption thirdOption = QuestionOption.builder()
                                                    .id(3L)
                                                    .description("Option 3")
                                                    .questionItem(questionItem)
                                                    .build();
        List<QuestionOption> questionOptions = Arrays.asList(firstOption, secondOption, thirdOption);
        questionItem.getQuestionOptions().addAll(questionOptions);

        return questionItem;
    }

    // Question - TEXT
    public static QuestionItem createJpaTestTextQuestionItem() {
        return QuestionItem.builder()
                           .description("Question Description")
                           .example("Question Example")
                           .inputType(InputType.TEXT)
                           .questionType(createQuestionType())
                           .build();
    }
}
