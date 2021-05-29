package com.careerzip.domain.archive.dto.response.archivesresponse;

import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.project.entity.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class RelatedProject {

    @JsonIgnore
    private final long archiveId;

    private final long id;

    private final String title;

    @Builder
    private RelatedProject(long id, long archiveId, String title) {
        this.id = id;
        this.archiveId = archiveId;
        this.title = title;
    }

    public static RelatedProject of(Project project, Archive archive) {
        return RelatedProject.builder()
                             .id(project.getId())
                             .archiveId(archive.getId())
                             .title(project.getTitle())
                             .build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, archiveId, title);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof RelatedProject)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        return this.id == ((RelatedProject) obj).getId();
    }
}
