package com.careerzip.testobject.questionitem;

import com.careerzip.domain.questionitem.entity.InputType;
import com.careerzip.domain.questionitem.entity.QuestionItem;
import com.careerzip.domain.questiontype.entity.QuestionType;
import com.careerzip.domain.selectoption.entity.SelectOption;

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

        SelectOption firstOption = SelectOption.builder()
                                               .id(1L)
                                               .description("Option 1")
                                               .questionItem(questionItem)
                                               .build();
        SelectOption secondOption = SelectOption.builder()
                                                .id(2L)
                                                .description("Option 2")
                                                .questionItem(questionItem)
                                                .build();
        SelectOption thirdOption = SelectOption.builder()
                                               .id(3L)
                                               .description("Option 3")
                                               .questionItem(questionItem)
                                               .build();
        List<SelectOption> selectOptions = Arrays.asList(firstOption, secondOption, thirdOption);
        questionItem.getSelectOptions().addAll(selectOptions);

        return questionItem;
    }

    // Question - TEXT
    public static QuestionItem createJpaTextQuestionItemOf(QuestionType questionType) {
        return QuestionItem.builder()
                           .description("Question Description")
                           .example("Question Example")
                           .inputType(InputType.TEXT)
                           .questionType(questionType)
                           .build();
    }
}
