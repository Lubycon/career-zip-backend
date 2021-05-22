package com.careerzip.domain.templatequestion.repository;

import com.careerzip.domain.template.entity.Template;
import com.careerzip.domain.templatequestion.entity.TemplateQuestion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.careerzip.domain.question.entity.QQuestion.question;
import static com.careerzip.domain.templatequestion.entity.QTemplateQuestion.templateQuestion;

@RequiredArgsConstructor
public class TemplateQuestionRepositoryImpl implements TemplateQuestionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<TemplateQuestion> findAllBy(Template template) {
        return queryFactory.selectFrom(templateQuestion)
                           .innerJoin(templateQuestion.question, question).fetchJoin()
                           .where(templateQuestion.template.eq(template))
                           .fetch();
    }
}
