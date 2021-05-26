package com.careerzip.domain.archiving.dto.response.archivingsresponse;

import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ArchivingSummary {

    private final long id;

    @NotNull
    private final String letterFormTitle;

    @NotNull
    private final String letterTitle;

    @NotNull
    private final LocalDateTime createdDateTime;

    @Builder
    private ArchivingSummary(long id, String letterFormTitle, String letterTitle, LocalDateTime createdDateTime) {
        this.id = id;
        this.letterFormTitle = letterFormTitle;
        this.letterTitle = letterTitle;
        this.createdDateTime = createdDateTime;
    }

    public static ArchivingSummary of(Archiving archiving, QuestionPaperForm questionPaperForm, QuestionPaper questionPaper) {
        return ArchivingSummary.builder()
                            .id(archiving.getId())
                            .letterFormTitle(questionPaperForm.getTitle())
                            .letterTitle(questionPaper.getTitle())
                            .build();
    }

    public static List<ArchivingSummary> listOf(Page<Archiving> page) {
        List<Archiving> archivings = page.getContent();

        return archivings.stream()
                      .map(archiving -> {
                          QuestionPaper questionPaper = archiving.getQuestionPaper();
                          QuestionPaperForm questionPaperForm = questionPaper.getQuestionPaperForm();
                          return of(archiving, questionPaperForm, questionPaper);
                      }).collect(Collectors.toList());
    }
}
