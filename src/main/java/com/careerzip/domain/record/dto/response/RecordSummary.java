package com.careerzip.domain.record.dto.response;

import com.careerzip.domain.questionnaire.entity.Questionnaire;
import com.careerzip.domain.record.entity.Record;
import com.careerzip.domain.template.entity.Template;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RecordSummary {

    private final long id;

    @NotNull
    private final String templateTitle;

    @NotNull
    private final String questionnaireTitle;

    @NotNull
    private final LocalDateTime createdDateTime;

    @Builder
    private RecordSummary(long id, String templateTitle, String questionnaireTitle, LocalDateTime createdDateTime) {
        this.id = id;
        this.templateTitle = templateTitle;
        this.questionnaireTitle = questionnaireTitle;
        this.createdDateTime = createdDateTime;
    }

    public static RecordSummary of(Record record, Template template, Questionnaire questionnaire) {
        return RecordSummary.builder()
                            .id(record.getId())
                            .templateTitle(template.getTitle())
                            .questionnaireTitle(questionnaire.getTitle())
                            .build();
    }

    public static List<RecordSummary> listOf(Page<Record> page) {
        List<Record> records = page.getContent();

        return records.stream()
                      .map(record -> {
                          Questionnaire questionnaire = record.getQuestionnaire();
                          Template template = questionnaire.getTemplate();
                          return of(record, template, questionnaire);
                      }).collect(Collectors.toList());
    }
}
