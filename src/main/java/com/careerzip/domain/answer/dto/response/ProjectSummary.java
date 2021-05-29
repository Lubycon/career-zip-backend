package com.careerzip.domain.answer.dto.response;

import com.careerzip.domain.project.entity.Project;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectSummary {

    private final long id;

    private final String title;

    @Builder
    private ProjectSummary(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static ProjectSummary from(Project project) {
        return ProjectSummary.builder()
                             .id(project.getId())
                             .title(project.getTitle())
                             .build();
    }
}
