package com.careerzip.domain.questionpaper.dto;

import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LatestQuestionPaperResponse {

    private final long id;

    @NotNull
    private final LocalDate startDate;

    @NotNull
    private final LocalDate endDate;

    @NotNull
    private final List<QuestionDetail> questions;

    @Builder
    private LatestQuestionPaperResponse(long id, LocalDate startDate, LocalDate endDate,
                                        List<QuestionDetail> questions) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.questions = questions;
    }

    public static LatestQuestionPaperResponse of(QuestionPaper questionPaper, List<Question> questions) {
        List<QuestionDetail> questionDetails = questions.stream()
                                                        .map(QuestionDetail::from)
                                                        .collect(Collectors.toList());;

        return LatestQuestionPaperResponse.builder()
                                          .id(questionPaper.getId())
                                          .startDate(questionPaper.getStartDateTime().toLocalDate())
                                          .endDate(questionPaper.getEndDateTime().toLocalDate())
                                          .questions(questionDetails)
                                          .build();
    }
}
