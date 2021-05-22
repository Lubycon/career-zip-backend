package com.careerzip.domain.record.dto.response.recorddetailresponse;

import com.careerzip.domain.answeroption.AnswerOption;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.entity.QuestionType;
import com.careerzip.domain.templatequestion.entity.TemplateQuestion;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class QuestionDetail {

    private final int priority;

    @NotNull
    private final QuestionType type;

    @NotNull
    private final String description;

    @NotNull
    private final List<String> answerOptions;

    @Nullable
    private final List<AnswerDetail> answers;

    @Builder
    private QuestionDetail(int priority, QuestionType type, String description, List<String> answerOptions, List<AnswerDetail> answers) {
        this.priority = priority;
        this.type = type;
        this.description = description;
        this.answerOptions = answerOptions;
        this.answers = answers;
    }

    public static QuestionDetail of(TemplateQuestion templateQuestion, Question question, List<AnswerDetail> answers) {
        List<String> answerOptions = question.getAnswerOptions().stream()
                                                                .map(AnswerOption::getDescription)
                                                                .collect(Collectors.toList());

        return QuestionDetail.builder()
                             .priority(templateQuestion.getPriority())
                             .type(question.getQuestionType())
                             .description(question.getDescription())
                             .answerOptions(answerOptions)
                             .answers(answers)
                             .build();
    }
}
