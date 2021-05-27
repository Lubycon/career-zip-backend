package com.careerzip.domain.archive.dto.response.archivedetailresponse;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.questionitem.entity.InputType;
import com.careerzip.domain.questionitem.entity.QuestionItem;
import com.careerzip.domain.selectoption.entity.SelectOption;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class QuestionWithAnswers {

    private final long id;

    private final int priority;

    @NotNull
    private final InputType inputType;

    @NotNull
    private final String description;

    @NotNull
    private final String example;

    @NotNull
    private final List<String> selectOptions;

    @NotNull
    private final List<AnswerDetail> answers;

    @Builder
    private QuestionWithAnswers(long id, int priority, InputType inputType, String description, String example,
                                List<String> selectOptions, List<AnswerDetail> answers) {
        this.id = id;
        this.priority = priority;
        this.inputType = inputType;
        this.description = description;
        this.example = example;
        this.selectOptions = selectOptions;
        this.answers = answers;
    }

    public static QuestionWithAnswers of(Question question, List<Answer> answers) {
        if (answers == null) {
            answers = Collections.emptyList();
        }

        QuestionItem questionItem = question.getQuestionItem();
        List<String> selectOptions = questionItem.getSelectOptions()
                .stream()
                .map(SelectOption::getDescription)
                .collect(Collectors.toList());

        return QuestionWithAnswers.builder()
                .priority(question.getPriority())
                .inputType(questionItem.getInputType())
                .description(questionItem.getDescription())
                .selectOptions(selectOptions)
                .answers(AnswerDetail.listOf(answers))
                .build();
    }

    public static List<QuestionWithAnswers> listOf(List<Question> questions, List<Answer> answers) {
        Map<Long, List<Answer>> answersMap = answers.stream()
                                                    .collect(Collectors.groupingBy(Answer::getQuestionId));
        return questions.stream()
                        .map(question -> {
                            List<Answer> relatedAnswers = answersMap.get(question.getId());
                            return of(question, relatedAnswers);
                        }).collect(Collectors.toList());
    }
}
