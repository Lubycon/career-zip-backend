package com.careerzip.domain.answer.dto.request;

import java.util.List;

public class PreviousAnswersRequestBuilder {

    public static PreviousAnswersRequestBuilder.Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        Long questionPaperId;
        List<Long> projectIds;

        public Builder questionPaperId(Long questionPaperId) {
            this.questionPaperId = questionPaperId;
            return this;
        }

        public Builder projectIds(List<Long> projectIds) {
            this.projectIds = projectIds;
            return this;
        }

        public PreviousAnswersRequest build() {
            return new PreviousAnswersRequest(questionPaperId, projectIds);
        }
    }
}
