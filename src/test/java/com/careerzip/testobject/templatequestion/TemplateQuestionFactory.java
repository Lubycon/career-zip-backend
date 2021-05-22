package com.careerzip.testobject.templatequestion;

import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.template.entity.Template;
import com.careerzip.domain.templatequestion.entity.TemplateQuestion;

import static com.careerzip.testobject.template.TemplateFactory.createTemplate;

public class TemplateQuestionFactory {

    // TemplateQuestion
    public static TemplateQuestion createTemplateQuestionOf(Question question) {
        return TemplateQuestion.builder()
                               .id(1L)
                               .template(createTemplate())
                               .question(question)
                               .priority(1)
                               .build();
    }

    public static TemplateQuestion createJpaTestTemplateQuestionOf(Template template, Question question) {
            return TemplateQuestion.builder()
                                   .template(template)
                                   .question(question)
                                   .priority(1)
                                   .build();
    }
}
