package com.careerzip.testobject.questionnaire;

import com.careerzip.domain.questionnaire.entity.Questionnaire;
import com.careerzip.domain.template.entity.Template;

import java.time.LocalDateTime;

import static com.careerzip.testobject.template.TemplateFactory.createTemplate;

public class QuestionnaireFactory {

    // Questionnaire
    public static Questionnaire createQuestionnaire() {
        return Questionnaire.builder()
                            .id(1L)
                            .title("Questionnaire Title")
                            .startDateTime(LocalDateTime.of(2021, 05, 20, 5, 2, 1))
                            .endDateTime(LocalDateTime.of(2021, 05, 20, 7, 2, 1))
                            .template(createTemplate())
                            .build();
    }

    public static Questionnaire createJpaTestQuestionnaireOf(Template template) {
        return Questionnaire.builder()
                            .title("Questionnaire Title")
                            .startDateTime(LocalDateTime.of(2021, 05, 20, 5, 2, 1))
                            .endDateTime(LocalDateTime.of(2021, 05, 20, 7, 2, 1))
                            .template(template)
                            .build();
    }
}
