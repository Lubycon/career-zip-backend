package com.careerzip.domain.answer.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PreviousAnswersRequest {

    private Long questionPaperId;
    private List<Long> projectIds;

    PreviousAnswersRequest(Long questionPaperId, List<Long> projectIds) {
        this.questionPaperId = questionPaperId;
        this.projectIds = projectIds;
    }
}
