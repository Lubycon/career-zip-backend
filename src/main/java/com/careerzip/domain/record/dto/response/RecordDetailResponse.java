package com.careerzip.domain.record.dto.response;

import com.careerzip.domain.question.dto.response.QuestionDetail;
import com.careerzip.domain.questionnaire.entity.Questionnaire;
import com.careerzip.domain.record.entity.Record;
import com.careerzip.domain.template.entity.Template;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RecordDetailResponse {

    @JsonProperty("recordId")
    private final long id;

    @NotNull
    private final String templateTitle;

    @NotNull
    private final String questionnaireTitle;

    @NotNull
    private final LocalDateTime createdDateTime;

    @NotNull
    private final List<QuestionDetail> questions;

    @Builder
    private RecordDetailResponse(long id, String templateTitle, String questionnaireTitle, LocalDateTime createdDateTime,
                                List<QuestionDetail> questions) {
        this.id = id;
        this.templateTitle = templateTitle;
        this.questionnaireTitle = questionnaireTitle;
        this.createdDateTime = createdDateTime;
        this.questions = questions;
    }

    public static RecordDetailResponse of(Record record, Template template, Questionnaire questionnaire, List<QuestionDetail> questions) {
        return RecordDetailResponse.builder()
                                   .id(record.getId())
                                   .templateTitle(template.getTitle())
                                   .questionnaireTitle(questionnaire.getTitle())
                                   .questions(questions)
                                   .build();
    }
}
