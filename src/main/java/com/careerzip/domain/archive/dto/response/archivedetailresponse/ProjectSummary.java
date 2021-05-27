package com.careerzip.domain.archive.dto.response.archivedetailresponse;

import com.careerzip.domain.project.entity.Project;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ProjectSummary)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        return this.id == ((ProjectSummary) obj).getId();
    }
}
