package com.careerzip.domain.letterformquestion.repository;

import com.careerzip.domain.answer.entity.QAnswer;
import com.careerzip.domain.letterform.entity.LetterForm;
import com.careerzip.domain.letterformquestion.entity.LetterFormQuestion;
import com.careerzip.domain.question.entity.QQuestion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.careerzip.domain.answer.entity.QAnswer.answer;
import static com.careerzip.domain.letterformquestion.entity.QLetterFormQuestion.letterFormQuestion;
import static com.careerzip.domain.question.entity.QQuestion.question;

@RequiredArgsConstructor
public class LetterFormQuestionRepositoryImpl implements LetterFormQuestionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<LetterFormQuestion> findAllBy(LetterForm letterForm) {
        return queryFactory.selectFrom(letterFormQuestion)
                           .innerJoin(letterFormQuestion.question, question).fetchJoin()
                           .leftJoin(letterFormQuestion.answers, answer).fetchJoin()
                           .where(letterFormQuestion.letterForm.eq(letterForm))
                           .fetch();
    }
}
