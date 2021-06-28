package com.careerzip.global.admin.dto.request;

import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class CreateQuestionPaperRequest {

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate finishDate;

    public QuestionPaper toEntity() {
        LocalTime startTime = LocalTime.of(0, 0, 0);
        LocalTime endTime = LocalTime.of(23, 59, 59);
        LocalTime finishTime = LocalTime.of(23, 55, 0);

        return QuestionPaper.builder()
                            .title(title)
                            .startDateTime(LocalDateTime.of(startDate, startTime))
                            .endDateTime(LocalDateTime.of(endDate, endTime))
                            .finishDateTime(LocalDateTime.of(finishDate, finishTime))
                            .build();
    }
}
