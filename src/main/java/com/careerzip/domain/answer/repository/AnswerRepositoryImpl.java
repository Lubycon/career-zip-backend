package com.careerzip.domain.answer.repository;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.careerarchive.entity.CareerArchive;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.careerzip.domain.answer.entity.QAnswer.answer;
import static com.careerzip.domain.project.entity.QProject.project;

@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Answer> findAllBy(CareerArchive careerArchive, List<Long> questionIds) {
        return queryFactory.selectFrom(answer)
                           .leftJoin(answer.project, project).fetchJoin()
                           .where(answer.archiving.eq(careerArchive), answer.question.id.in(questionIds))
                           .fetch();
    }
}
