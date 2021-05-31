package com.careerzip.domain.archive.dto.request.createarchiverequest;

public class CreateAnswerDetailBuilder {

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private long questionId;
        private long projectId;
        private String comment;

        public Builder questionId(long questionId) {
            this.questionId = questionId;
            return this;
        }

        public Builder projectId(long projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public CreateAnswerDetail build() {
            return new CreateAnswerDetail(questionId, projectId, comment);
        }
    }
}
