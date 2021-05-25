package com.careerzip.domain.archiving.dto.response.archivingdetailresponse;

import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.letter.entity.Letter;
import com.careerzip.domain.letterform.entity.LetterForm;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ArchivingDetailResponse {

    @JsonProperty("archivingId")
    private final long id;

    @NotNull
    private final String letterFormTitle;

    @NotNull
    private final String letterTitle;

    @NotNull
    private final LocalDateTime createdDateTime;

    @NotNull
    private final List<QuestionWithAnswers> questions;

    @Builder
    private ArchivingDetailResponse(long id, String letterFormTitle, String letterTitle, LocalDateTime createdDateTime,
                                    List<QuestionWithAnswers> questions) {
        this.id = id;
        this.letterFormTitle = letterFormTitle;
        this.letterTitle = letterTitle;
        this.createdDateTime = createdDateTime;
        this.questions = questions;
    }

    public static ArchivingDetailResponse of(Archiving archiving, List<QuestionWithAnswers> questions) {
        Letter letter = archiving.getLetter();
        LetterForm letterForm = letter.getLetterForm();

        return ArchivingDetailResponse.builder()
                                      .id(archiving.getId())
                                      .letterFormTitle(letterForm.getTitle())
                                      .letterTitle(letter.getTitle())
                                      .questions(questions)
                                      .build();
    }
}
