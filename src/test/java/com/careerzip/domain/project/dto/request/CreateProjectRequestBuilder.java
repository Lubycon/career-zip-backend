package com.careerzip.domain.project.dto.request;

import java.time.LocalDate;

public class CreateProjectRequestBuilder {

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String description;
        private String aim;
        private String role;
        private String mainBusiness;
        private Integer participantsCount;
        private Double contribution;
        private String teamMembers;
        private LocalDate startDate;
        private LocalDate endDate;

        public Builder() {}

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder aim(String aim) {
            this.aim = aim;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder mainBusiness(String mainBusiness) {
            this.mainBusiness = mainBusiness;
            return this;
        }

        public Builder participantsCount(int participantsCount) {
            this.participantsCount = participantsCount;
            return this;
        }

        public Builder contribution(double contribution) {
            this.contribution = contribution;
            return this;
        }

        public Builder teamMembers(String teamMembers) {
            this.teamMembers = teamMembers;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public CreateProjectRequest build() {
            return new CreateProjectRequest(title, description, aim, role, mainBusiness, participantsCount, contribution,
                                            teamMembers, startDate, endDate);
        }
    }
}
