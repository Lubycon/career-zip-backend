package com.careerzip.testobject.letter;

import com.careerzip.domain.letter.entity.Letter;
import com.careerzip.domain.letterform.entity.LetterForm;

import java.time.LocalDateTime;

import static com.careerzip.testobject.letterform.LetterFormFactory.createLetterForm;

public class LetterFactory {

    // Questionnaire
    public static Letter createLetter() {
        return Letter.builder()
                            .id(1L)
                            .title("Questionnaire Title")
                            .startDateTime(LocalDateTime.of(2021, 05, 20, 5, 2, 1))
                            .endDateTime(LocalDateTime.of(2021, 05, 20, 7, 2, 1))
                            .letterForm(createLetterForm())
                            .build();
    }

    public static Letter createJpaTestLetterOf(LetterForm letterForm) {
        return Letter.builder()
                            .title("Questionnaire Title")
                            .startDateTime(LocalDateTime.of(2021, 05, 20, 5, 2, 1))
                            .endDateTime(LocalDateTime.of(2021, 05, 20, 7, 2, 1))
                            .letterForm(letterForm)
                            .build();
    }
}
