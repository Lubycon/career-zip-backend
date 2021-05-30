package com.careerzip.domain.questionpaper.dto;

import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.questionitem.entity.InputType;
import com.careerzip.domain.questionitem.entity.QuestionItem;
import com.careerzip.domain.selectoption.entity.SelectOption;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class QuestionDetail {

    private final long questionId;

    private final int priority;

    @NotNull
    private final InputType inputType;

    @NotNull
    private final String description;

    @NotNull
    private final String example;

    @NotNull
    private final List<String> selectOptions;

    @Builder
    private QuestionDetail(long questionId, int priority, InputType inputType, String description, String example,
                           List<String> selectOptions) {
        this.questionId = questionId;
        this.priority = priority;
        this.inputType = inputType;
        this.description = description;
        this.example = example;
        this.selectOptions = selectOptions;
    }

    public static QuestionDetail from(Question question) {
        QuestionItem questionItem = question.getQuestionItem();
        List<String> selectOptions = questionItem.getSelectOptions()
                                                 .stream()
                                                 .map(SelectOption::getDescription)
                                                 .collect(Collectors.toList());

        return QuestionDetail.builder()
                             .questionId(question.getId())
                             .priority(question.getPriority())
                             .inputType(questionItem.getInputType())
                             .description(questionItem.getDescription())
                             .example(questionItem.getExample())
                             .selectOptions(selectOptions)
                             .build();
    }
}
