package com.careerzip.global.admin.dto.response;

import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class NotArchivedContactsResponse {

    private final long questionPaperId;

    @JsonProperty("accounts")
    private final List<ContactSummary> contacts;

    @Builder
    private NotArchivedContactsResponse(long questionPaperId, List<ContactSummary> contacts) {
        this.questionPaperId = questionPaperId;
        this.contacts = contacts;
    }

    public static NotArchivedContactsResponse of(QuestionPaper questionPaper, List<ContactSummary> contacts) {
        return NotArchivedContactsResponse.builder()
                                          .questionPaperId(questionPaper.getId())
                                          .contacts(contacts)
                                          .build();
    }
}
