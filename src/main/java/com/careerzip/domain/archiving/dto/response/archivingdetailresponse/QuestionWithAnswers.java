package com.careerzip.domain.archiving.dto.response.archivingdetailresponse;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.questionoption.QuestionOption;
import com.careerzip.domain.questionitem.entity.QuestionItem;
import com.careerzip.domain.questionitem.entity.QuestionType;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class QuestionWithAnswers {

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
    private QuestionWithAnswers(int priority, QuestionType type, String description, List<String> answerOptions,
                                List<AnswerDetail> answers) {
        this.priority = priority;
        this.type = type;
        this.description = description;
        this.answerOptions = answerOptions;
        this.answers = answers;
    }

    public static QuestionWithAnswers of(Question question, List<Answer> answers) {
        QuestionItem questionItem = question.getQuestionItem();
        List<String> answerOptions = questionItem.getQuestionOptions().stream()
                                                                .map(QuestionOption::getDescription)
                                                                .collect(Collectors.toList());

        return QuestionWithAnswers.builder()
                                  .priority(question.getPriority())
                                  .type(questionItem.getQuestionType())
                                  .description(questionItem.getDescription())
                                  .answerOptions(answerOptions)
                                  .answers(AnswerDetail.listOf(answers))
                                  .build();
    }

    public static List<QuestionWithAnswers> listOf(List<Question> questions,
                                                   Map<Long, List<Answer>> answersMap) {
        return questions.stream()
                        .map(question -> {
                            List<Answer> answers = answersMap.get(question.getId());
                            return of(question, answers);
                        }).collect(Collectors.toList());
    }
}
