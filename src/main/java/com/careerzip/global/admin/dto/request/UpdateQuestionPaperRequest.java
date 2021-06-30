package com.careerzip.global.admin.dto.request;

import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class UpdateQuestionPaperRequest {

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate finishDate;

    public QuestionPaper toEntity(QuestionPaper questionPaper) {
        LocalTime startTime = LocalTime.of(0, 0, 0);
        LocalTime endTime = LocalTime.of(23, 59, 59);
        LocalTime finishTime = LocalTime.of(23, 55, 0);

        return questionPaper.update(title, LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime), LocalDateTime.of(finishDate, finishTime));

    }
}
