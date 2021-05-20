package com.careerzip.testobject.templatequestion;

import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.templatequestion.entity.TemplateQuestion;

import static com.careerzip.testobject.template.TemplateFactory.createTemplate;

public class TemplateQuestionFactory {

    // TemplateQuestion
    public static TemplateQuestion createTemplateQuestionOf(Question question) {
        return TemplateQuestion.builder()
                               .id(1L)
                               .template(createTemplate())
                               .question(question)
                               .order(1)
                               .build();
    }
}
