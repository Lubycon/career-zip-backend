package com.careerzip.domain.question.repository;

import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.template.entity.Template;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.careerzip.domain.question.entity.QQuestion.question;
import static com.careerzip.domain.questiontemplate.entity.QQuestionTemplate.questionTemplate;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Question> findAllBy(Template template) {
        return queryFactory.selectFrom(question)
                           .innerJoin(question.questionTemplate, questionTemplate).fetchJoin()
                           .where(question.template.eq(template))
                           .fetch();
    }
}
