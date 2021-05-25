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

    public static ArchivingSummary of(Archiving archiving, LetterForm letterForm, Letter letter) {
        return ArchivingSummary.builder()
                            .id(archiving.getId())
                            .letterFormTitle(letterForm.getTitle())
                            .letterTitle(letter.getTitle())
                            .build();
    }

    public static List<ArchivingSummary> listOf(Page<Archiving> page) {
        List<Archiving> archivings = page.getContent();

        return archivings.stream()
                      .map(archiving -> {
                          Letter letter = archiving.getLetter();
                          LetterForm letterForm = letter.getLetterForm();
                          return of(archiving, letterForm, letter);
                      }).collect(Collectors.toList());
    }
}
