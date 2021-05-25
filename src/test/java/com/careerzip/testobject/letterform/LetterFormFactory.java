package com.careerzip.testobject.letterform;

import com.careerzip.domain.letterform.entity.LetterForm;

public class LetterFormFactory {

    // LetterForm
    public static LetterForm createLetterForm() {
        return LetterForm.builder()
                         .id(1L)
                         .title("Template title")
                         .build();
    }

    public static LetterForm createJpaTestLetterForm() {
        return LetterForm.from("New Template");
    }
}
