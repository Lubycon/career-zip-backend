package com.careerzip.domain.question.repository;

import com.careerzip.domain.letterform.entity.LetterForm;
import com.careerzip.domain.question.entity.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.careerzip.domain.question.entity.QQuestion.question;
import static com.careerzip.domain.questionitem.entity.QQuestionItem.questionItem;
import static com.careerzip.domain.selectoption.QQuestionOption.questionOption;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Question> findAllBy(LetterForm letterForm) {
        return queryFactory.selectFrom(question)
                           .innerJoin(question.questionItem, questionItem).fetchJoin()
                           .leftJoin(questionItem.questionOptions, questionOption).fetchJoin()
                           .where(question.letterForm.eq(letterForm))
                           .fetch();
    }
}
