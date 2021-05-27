package com.careerzip.domain.archive.dto.response.archivedetailresponse;

import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
public class ArchiveDetailResponse {

    private final long archiveId;

    @NotNull
    private final LocalDate startDate;

    @NotNull
    private final LocalDate endDate;

    @NotNull
    private final LocalDateTime createdDateTime;

    @NotNull
    private final Set<ProjectSummary> selectedProjects;

    @NotNull
    private final List<QuestionWithAnswers> questions;

    @Builder
    private ArchiveDetailResponse(long archiveId, LocalDate startDate, LocalDate endDate, LocalDateTime createdDateTime,
                                  Set<ProjectSummary> selectedProjects, List<QuestionWithAnswers> questions) {
        this.archiveId = archiveId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDateTime = createdDateTime;
        this.selectedProjects = selectedProjects;
        this.questions = questions;
    }

    public static ArchiveDetailResponse of(Archive archive, Set<ProjectSummary> selectedProjects, List<QuestionWithAnswers> questions) {
        QuestionPaper questionPaper = archive.getQuestionPaper();

        return ArchiveDetailResponse.builder()
                                    .archiveId(archive.getId())
                                    .startDate(questionPaper.getStartDateTime().toLocalDate())
                                    .endDate(questionPaper.getEndDateTime().toLocalDate())
                                    .createdDateTime(archive.getCreatedDateTime())
                                    .selectedProjects(selectedProjects)
                                    .questions(questions)
                                    .build();
    }
}
