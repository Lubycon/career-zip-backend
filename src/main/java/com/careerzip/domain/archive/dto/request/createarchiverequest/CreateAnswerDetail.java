package com.careerzip.domain.archive.dto.request.createarchiverequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreateAnswerDetail {

    private long questionId;
    private long projectId;
    private String comment;

    CreateAnswerDetail(long questionId, long projectId, String comment) {
        this.questionId = questionId;
        this.projectId = projectId;
        this.comment = comment;
    }
}
