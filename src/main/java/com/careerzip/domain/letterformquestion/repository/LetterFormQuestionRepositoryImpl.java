package com.careerzip.domain.letterformquestion.repository;

import com.careerzip.domain.letterformquestion.entity.LetterFormQuestion;
import com.careerzip.domain.letterform.entity.LetterForm;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.careerzip.domain.answer.entity.QAnswer.answer;
import static com.careerzip.domain.letterformquestion.entity.QQuestion.question;
import static com.careerzip.domain.question.entity.QQuestionTemplate.questionTemplate;

@RequiredArgsConstructor
public class LetterFormQuestionRepositoryImpl implements LetterFormQuestionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<LetterFormQuestion> findAllBy(LetterForm letterForm) {
        return queryFactory.selectFrom(question)
                           .innerJoin(question.questionTemplate, questionTemplate).fetchJoin()
                           .leftJoin(question.answers, answer).fetchJoin()
                           .where(question.template.eq(letterForm))
                           .fetch();
    }
}
