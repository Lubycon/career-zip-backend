package com.careerzip.domain.project.dto.response;

import com.careerzip.domain.project.entity.Project;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProjectSummaryResponse {

    private final long id;

    private final String title;

    @Builder
    private ProjectSummaryResponse(long id, String title) {
        this.id = id;
        this.title = title;
    }

    private static ProjectSummaryResponse from(Project project) {
        return ProjectSummaryResponse.builder()
                                     .id(project.getId())
                                     .title(project.getTitle())
                                     .build();
    }

    public static List<ProjectSummaryResponse> listOf(List<Project> projects) {
        return projects.stream()
                       .map(ProjectSummaryResponse::from)
                       .collect(Collectors.toList());
    }
}
