package com.careerzip.domain.archiving.dto.response.archivingdetailresponse;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.questionoption.QuestionOption;
import com.careerzip.domain.questionitem.entity.QuestionItem;
import com.careerzip.domain.questionitem.entity.InputType;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class QuestionWithAnswers {

    private final int priority;

    @NotNull
    private final InputType inputType;

    @NotNull
    private final String description;

    @NotNull
    private final List<String> answerOptions;

    @Nullable
    private final List<AnswerDetail> answers;

    @Builder
    private QuestionWithAnswers(int priority, InputType inputType, String description, List<String> answerOptions,
                                List<AnswerDetail> answers) {
        this.priority = priority;
        this.inputType = inputType;
        this.description = description;
        this.answerOptions = answerOptions;
        this.answers = answers;
    }

    public static QuestionWithAnswers of(Question question, List<Answer> answers) {
        if (answers == null) {
            answers = Collections.emptyList();
        }

        QuestionItem questionItem = question.getQuestionItem();
        List<String> answerOptions = questionItem.getQuestionOptions()
                                                 .stream()
                                                 .map(QuestionOption::getDescription)
                                                 .collect(Collectors.toList());

        return QuestionWithAnswers.builder()
                                  .priority(question.getPriority())
                                  .inputType(questionItem.getInputType())
                                  .description(questionItem.getDescription())
                                  .answerOptions(answerOptions)
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
