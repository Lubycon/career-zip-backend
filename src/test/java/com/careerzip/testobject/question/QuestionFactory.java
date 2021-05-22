package com.careerzip.testobject.question;

import com.careerzip.domain.questiontemplate.entity.QuestionTemplate;
import com.careerzip.domain.template.entity.Template;
import com.careerzip.domain.question.entity.Question;

import static com.careerzip.testobject.template.TemplateFactory.createTemplate;

public class QuestionFactory {

    // TemplateQuestion
    public static Question createQuestionOf(QuestionTemplate questionTemplate) {
        return Question.builder()
                               .id(1L)
                               .template(createTemplate())
                               .questionTemplate(questionTemplate)
                               .priority(1)
                               .build();
    }

    public static Question createJpaTestQuestionOf(Template template, QuestionTemplate questionTemplate) {
            return Question.builder()
                                   .template(template)
                                   .questionTemplate(questionTemplate)
                                   .priority(1)
                                   .build();
    }
}
