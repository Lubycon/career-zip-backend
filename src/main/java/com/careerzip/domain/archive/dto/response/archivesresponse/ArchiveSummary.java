package com.careerzip.domain.archive.dto.response.archivesresponse;

import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ArchiveSummary {

    private final long id;

    @NotNull
    private final LocalDate startDate;

    @NotNull
    private final LocalDate endDate;

    @NotNull
    private final LocalDateTime createdDateTime;

    @NotNull
    private final List<RelatedProject> projects;

    @Builder
    private ArchiveSummary(long id, LocalDate startDate, LocalDate endDate, LocalDateTime createdDateTime,
                           List<RelatedProject> projects) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDateTime = createdDateTime;
        this.projects = projects;
    }

    public static ArchiveSummary of(Archive archive, QuestionPaper questionPaper, List<RelatedProject> projects) {
        if (projects == null) {
            projects = Collections.emptyList();
        }

        return ArchiveSummary.builder()
                            .id(archive.getId())
                            .startDate(questionPaper.getStartDateTime().toLocalDate())
                            .endDate(questionPaper.getEndDateTime().toLocalDate())
                            .createdDateTime(archive.getCreatedDateTime())
                            .projects(projects)
                            .build();
    }

    public static List<ArchiveSummary> listOf(Page<Archive> archivePage, Set<RelatedProject> projects) {
        List<Archive> archives = archivePage.getContent();
        Map<Long, List<RelatedProject>> projectsMap = projects.stream()
                                                              .collect(Collectors.groupingBy(RelatedProject::getArchiveId));

        return archives.stream()
                      .map(archive -> {
                          QuestionPaper questionPaper = archive.getQuestionPaper();
                          List<RelatedProject> relatedProjects = projectsMap.get(archive.getId());
                          return of(archive, questionPaper, relatedProjects);
                      }).collect(Collectors.toList());
    }
}
