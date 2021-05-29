package com.careerzip.domain.answer.repository;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.project.entity.QProject;
import com.careerzip.domain.question.entity.QQuestion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.careerzip.domain.answer.entity.QAnswer.answer;
import static com.careerzip.domain.project.entity.QProject.project;
import static com.careerzip.domain.question.entity.QQuestion.question;

@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Answer> findAllBy(Archive archive, List<Long> questionIds) {
        return queryFactory.selectFrom(answer)
                           .leftJoin(answer.project, project).fetchJoin()
                           .where(answer.archive.eq(archive), answer.question.id.in(questionIds))
                           .fetch();
    }

    public List<Answer> findAllBy(List<Long> answerIds) {
        return queryFactory.selectFrom(answer)
                           .leftJoin(answer.project, project).fetchJoin()
                           .where(answer.id.in(answerIds))
                           .fetch();
    }

    public List<Long> findAllPreviousIdsBy(Long accountId, List<Long> projectIds, List<Long> questionIds) {
        return queryFactory.select(answer.id.max())
                           .from(answer)
                           .where(answer.account.id.eq(accountId),
                                  answer.project.id.in(projectIds),
                                  answer.question.id.in(questionIds))
                           .groupBy(answer.project, answer.question)
                           .fetch();

    }
}
