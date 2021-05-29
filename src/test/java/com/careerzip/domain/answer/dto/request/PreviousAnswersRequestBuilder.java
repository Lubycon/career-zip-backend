package com.careerzip.domain.answer.dto.request;

import java.util.List;

public class PreviousAnswersRequestBuilder {

    public static PreviousAnswersRequestBuilder.Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        List<Long> projectIds;

        public Builder projectIds(List<Long> projectIds) {
            this.projectIds = projectIds;
            return this;
        }

        public PreviousAnswersRequest build() {
            return new PreviousAnswersRequest(projectIds);
        }
    }
}
