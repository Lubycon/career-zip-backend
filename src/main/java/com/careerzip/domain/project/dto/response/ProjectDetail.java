package com.careerzip.domain.project.dto.response;

import com.careerzip.domain.project.entity.Project;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public class ProjectDetail {

    private long id;

    @Nullable
    private String title;

    @Builder
    private ProjectDetail(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static ProjectDetail from(Project project) {
        return ProjectDetail.builder()
                            .id(project.getId())
                            .title(project.getTitle())
                            .build();
    }
}
