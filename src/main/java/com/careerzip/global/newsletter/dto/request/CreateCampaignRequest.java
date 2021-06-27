package com.careerzip.global.newsletter.dto.request;

import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class CreateCampaignRequest {

    private final String name;
    private final String languageCode;
    private final boolean isDefault;

    @Builder
    private CreateCampaignRequest(String name, String languageCode, boolean isDefault) {
        this.name = name;
        this.languageCode = languageCode;
        this.isDefault = isDefault;
    }

    public static CreateCampaignRequest from(QuestionPaper questionPaper) {
        String name = new StringBuilder("week")
                            .append(questionPaper.getId().toString())
                            .append("-reminder-")
                            .append(LocalDate.now())
                            .append("-")
                            .append(LocalTime.now().getMinute())
                            .append("-")
                            .append(LocalTime.now().getSecond())
                            .toString();

        return CreateCampaignRequest.builder()
                                    .name(name)
                                    .languageCode("KO")
                                    .isDefault(false)
                                    .build();
    }
}
