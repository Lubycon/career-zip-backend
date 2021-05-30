package com.careerzip.domain.answer.dto.response;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.question.entity.Question;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class PreviousAnswersWithQuestion {

    private final long id;

    @NotNull
    private final List<PreviousAnswer> answers;

    @Builder
    private PreviousAnswersWithQuestion(long id, List<PreviousAnswer> answers) {
        this.id = id;
        this.answers = answers;
    }

    public static PreviousAnswersWithQuestion of(Question question, List<Answer> answers) {
        if (answers == null) {
            answers = Collections.emptyList();
        }

        List<PreviousAnswer> previousAnswers = answers.stream()
                                                      .map(PreviousAnswer::from)
                                                      .collect(Collectors.toList());

        return PreviousAnswersWithQuestion.builder()
                                          .id(question.getId())
                                          .answers(previousAnswers)
                                          .build();
    }

    public static List<PreviousAnswersWithQuestion> listOf(List<Question> questions, List<Answer> answers) {
        Map<Long, List<Answer>> answersMap = answers.stream()
                                                    .collect(Collectors.groupingBy(Answer::getQuestionId));

        return questions.stream()
                        .map(question -> {
                            List<Answer> relatedAnswers = answersMap.get(question.getId());
                            return PreviousAnswersWithQuestion.of(question, relatedAnswers);
                        }).collect(Collectors.toList());

    }
}
