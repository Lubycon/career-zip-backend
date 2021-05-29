package com.careerzip.domain.archive.dto.response.archivingsresponse;

import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    @Builder
    private ArchiveSummary(long id, LocalDate startDate, LocalDate endDate, LocalDateTime createdDateTime) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDateTime = createdDateTime;
    }

    public static ArchiveSummary of(Archive archive, QuestionPaperForm questionPaperForm, QuestionPaper questionPaper) {
        return ArchiveSummary.builder()
                            .id(archive.getId())
                            .startDate(questionPaper.getStartDateTime().toLocalDate())
                            .endDate(questionPaper.getEndDateTime().toLocalDate())
                            .createdDateTime(archive.getCreatedDateTime())
                            .build();
    }

    public static List<ArchiveSummary> listOf(Page<Archive> page) {
        List<Archive> archives = page.getContent();

        return archives.stream()
                      .map(archive -> {
                          QuestionPaper questionPaper = archive.getQuestionPaper();
                          QuestionPaperForm questionPaperForm = questionPaper.getQuestionPaperForm();
                          return of(archive, questionPaperForm, questionPaper);
                      }).collect(Collectors.toList());
    }
}
