package com.careerzip.domain.archiving.dto.response.archivingdetailresponse;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.questiontype.entity.QuestionType;
import com.careerzip.domain.selectoption.entity.SelectOption;
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

    @Nullable
    private final Integer priority;

    @NotNull
    private final InputType inputType;

    @NotNull
    private final String questionType;

    @NotNull
    private final String description;

    @NotNull
    private final List<String> answerOptions;

    @Nullable
    private final List<AnswerDetail> answers;

    @Builder
    private QuestionWithAnswers(Integer priority, InputType inputType, String questionType, String description,
                                List<String> answerOptions, List<AnswerDetail> answers) {
        this.priority = priority;
        this.inputType = inputType;
        this.questionType = questionType;
        this.description = description;
        this.answerOptions = answerOptions;
        this.answers = answers;
    }

    public static QuestionWithAnswers of(Question question, List<Answer> answers) {
        if (answers == null) {
            answers = Collections.emptyList();
        }

        QuestionItem questionItem = question.getQuestionItem();
        QuestionType questionType = questionItem.getQuestionType();
        List<String> answerOptions = questionItem.getSelectOptions()
                                                 .stream()
                                                 .map(SelectOption::getDescription)
                                                 .collect(Collectors.toList());

        return QuestionWithAnswers.builder()
                                  .priority(question.getPriority())
                                  .inputType(questionItem.getInputType())
                                  .questionType(questionType.getName())
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
