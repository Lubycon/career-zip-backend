package com.careerzip.global.newsletter.dto.request;

import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class CreateCampaignRequest {

    private final String name;
    private final String languageCode;
    private final boolean isDefault;

    @JsonProperty("optinTypes")
    private final OptInTypes optInTypes;

    @Builder
    private CreateCampaignRequest(String name, String languageCode, boolean isDefault, OptInTypes optInTypes) {
        this.name = name;
        this.languageCode = languageCode;
        this.isDefault = isDefault;
        this.optInTypes = optInTypes;
    }

    public static CreateCampaignRequest from(QuestionPaper questionPaper) {
        String name = new StringBuilder("week")
                            .append(questionPaper.getId().toString())
                            .append("-reminder-")
                            .append(LocalDate.now())
                            .append("-")
                            .append(LocalTime.now().getHour())
                            .append("-")
                            .append(LocalTime.now().getMinute())
                            .append("-")
                            .append(LocalTime.now().getSecond())
                            .toString();

        return CreateCampaignRequest.builder()
                                    .name(name)
                                    .languageCode("KO")
                                    .isDefault(false)
                                    .optInTypes(OptInTypes.createSingleOptIn())
                                    .build();
    }
}
