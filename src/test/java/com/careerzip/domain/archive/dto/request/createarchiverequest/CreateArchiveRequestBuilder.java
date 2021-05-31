package com.careerzip.domain.archive.dto.request.createarchiverequest;

import java.util.List;

public class CreateArchiveRequestBuilder {

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private long questionPaperId;
        private List<CreateAnswerDetail> answers;

        public Builder questionPaperId(long questionPaperId) {
            this.questionPaperId = questionPaperId;
            return this;
        }

        public Builder answers(List<CreateAnswerDetail> answers) {
            this.answers = answers;
            return this;
        }

        public CreateArchiveRequest build() {
            return new CreateArchiveRequest(questionPaperId, answers);
        }
    }
}
