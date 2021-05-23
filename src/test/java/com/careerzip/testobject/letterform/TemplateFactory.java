package com.careerzip.testobject.letterform;

import com.careerzip.domain.letterform.entity.LetterForm;

public class TemplateFactory {

    // LetterForm
    public static LetterForm createLetterFormOf() {
        return LetterForm.builder()
                       .id(1L)
                       .title("Template title")
                       .build();
    }

    public static LetterForm createJpaTestLetterFormOf() {
        return LetterForm.from("New Template");
    }
}
