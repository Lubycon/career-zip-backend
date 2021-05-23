package com.careerzip.domain.archiving.dto.response.archivingdetailresponse;

import com.careerzip.domain.answeroption.AnswerOption;
import com.careerzip.domain.letterformquestion.entity.LetterFormQuestion;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.entity.QuestionType;
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

    public static QuestionDetail of(LetterFormQuestion letterFormQuestion, List<AnswerDetail> answers) {
        Question question = letterFormQuestion.getQuestion();
        List<String> answerOptions = question.getAnswerOptions().stream()
                                                                .map(AnswerOption::getDescription)
                                                                .collect(Collectors.toList());

        return QuestionDetail.builder()
                             .priority(letterFormQuestion.getPriority())
                             .type(question.getQuestionType())
                             .description(question.getDescription())
                             .answerOptions(answerOptions)
                             .answers(answers)
                             .build();
    }
}
