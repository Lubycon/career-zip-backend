package com.careerzip.testobject.questionnaire;

import com.careerzip.domain.questionnaire.entity.Questionnaire;
import com.careerzip.domain.template.entity.Template;

import java.time.LocalDateTime;

public class QuestionnaireFactory {

    public static Questionnaire createJpaTestQuestionnaireOf(Template template) {
        return Questionnaire.builder()
                            .startDateTime(LocalDateTime.of(2021, 05, 20, 5, 2, 1))
                            .endDateTime(LocalDateTime.of(2021, 05, 20, 7, 2, 1))
                            .template(template)
                            .build();
    }
}
