package com.careerzip.global.admin.dto.response;

import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AdminQuestionPaperDetail {

    private final long id;

    private final String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime startDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime endDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime finishDateTime;

    private final boolean opened;

    @Builder
    private AdminQuestionPaperDetail(long id, String title, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                     LocalDateTime finishDateTime, boolean opened) {
        this.id = id;
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.finishDateTime = finishDateTime;
        this.opened = opened;
    }

    public static AdminQuestionPaperDetail from(QuestionPaper questionPaper) {
        return AdminQuestionPaperDetail.builder()
                                       .id(questionPaper.getId())
                                       .title(questionPaper.getTitle())
                                       .startDateTime(questionPaper.getStartDateTime())
                                       .endDateTime(questionPaper.getEndDateTime())
                                       .opened(questionPaper.getOpened())
                                       .build();
    }

    public static List<AdminQuestionPaperDetail> listOf(List<QuestionPaper> questionPapers) {
        return questionPapers.stream()
                             .map(AdminQuestionPaperDetail::from)
                             .collect(Collectors.toList());
    }
}
