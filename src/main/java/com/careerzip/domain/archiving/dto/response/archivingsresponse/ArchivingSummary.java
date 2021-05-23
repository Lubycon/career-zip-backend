package com.careerzip.domain.archiving.dto.response.archivingsresponse;

import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.letter.entity.Letter;
import com.careerzip.domain.letterform.entity.LetterForm;
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
    private final String templateTitle;

    @NotNull
    private final String questionnaireTitle;

    @NotNull
    private final LocalDateTime createdDateTime;

    @Builder
    private ArchivingSummary(long id, String templateTitle, String questionnaireTitle, LocalDateTime createdDateTime) {
        this.id = id;
        this.templateTitle = templateTitle;
        this.questionnaireTitle = questionnaireTitle;
        this.createdDateTime = createdDateTime;
    }

    public static ArchivingSummary of(Archiving archiving, LetterForm letterForm, Letter letter) {
        return ArchivingSummary.builder()
                            .id(archiving.getId())
                            .templateTitle(letterForm.getTitle())
                            .questionnaireTitle(letter.getTitle())
                            .build();
    }

    public static List<ArchivingSummary> listOf(Page<Archiving> page) {
        List<Archiving> archivings = page.getContent();

        return archivings.stream()
                      .map(record -> {
                          Letter letter = record.getLetter();
                          LetterForm letterForm = letter.getLetterForm();
                          return of(record, letterForm, letter);
                      }).collect(Collectors.toList());
    }
}
