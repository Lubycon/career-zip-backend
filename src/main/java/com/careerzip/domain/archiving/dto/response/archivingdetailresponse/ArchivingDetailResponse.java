package com.careerzip.domain.archiving.dto.response.archivingdetailresponse;

import com.careerzip.domain.letter.entity.Letter;
import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.letterform.entity.LetterForm;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ArchivingDetailResponse {

    @JsonProperty("recordId")
    private final long id;

    @NotNull
    private final String templateTitle;

    @NotNull
    private final String questionnaireTitle;

    @NotNull
    private final LocalDateTime createdDateTime;

    @NotNull
    private final List<QuestionDetail> questions;

    @Builder
    private ArchivingDetailResponse(long id, String templateTitle, String questionnaireTitle, LocalDateTime createdDateTime,
                                    List<QuestionDetail> questions) {
        this.id = id;
        this.templateTitle = templateTitle;
        this.questionnaireTitle = questionnaireTitle;
        this.createdDateTime = createdDateTime;
        this.questions = questions;
    }

    public static ArchivingDetailResponse of(Archiving archiving, List<QuestionDetail> questions) {
        Letter letter = archiving.getLetter();
        LetterForm letterForm = letter.getLetterForm();

        return ArchivingDetailResponse.builder()
                                   .id(archiving.getId())
                                   .templateTitle(letterForm.getTitle())
                                   .questionnaireTitle(letter.getTitle())
                                   .questions(questions)
                                   .build();
    }
}
