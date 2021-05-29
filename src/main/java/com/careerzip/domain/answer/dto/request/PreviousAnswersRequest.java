package com.careerzip.domain.answer.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PreviousAnswersRequest {

    private List<Long> projectIds;

    PreviousAnswersRequest(List<Long> projectIds) {
        this.projectIds = projectIds;
    }
}
