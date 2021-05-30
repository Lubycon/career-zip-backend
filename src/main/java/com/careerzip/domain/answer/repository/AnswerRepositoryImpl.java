package com.careerzip.domain.answer.repository;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.questionitem.entity.QuestionItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.careerzip.domain.answer.entity.QAnswer.answer;
import static com.careerzip.domain.archive.entity.QArchive.archive;
import static com.careerzip.domain.project.entity.QProject.project;
import static com.careerzip.domain.question.entity.QQuestion.question;
import static com.careerzip.domain.questionitem.entity.QQuestionItem.questionItem;

@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Answer> findAllBy(Archive archive, List<Question> questions) {
        List<Long> questionIds = questions.stream()
                                          .map(Question::getId)
                                          .collect(Collectors.toList());

        return queryFactory.selectFrom(answer)
                           .leftJoin(answer.project, project).fetchJoin()
                           .where(answer.archive.eq(archive), answer.question.id.in(questionIds))
                           .fetch();
    }

    public List<Answer> findAllBy(List<Long> answerIds) {
        return queryFactory.selectFrom(answer)
                           .innerJoin(answer.question, question).fetchJoin()
                           .leftJoin(answer.project, project).fetchJoin()
                           .where(answer.id.in(answerIds))
                           .fetch();
    }

    public List<Long> findAllPreviousIdsBy(Long accountId, List<Long> projectIds, List<Question> questions) {
        List<Long> questionItemIds = questions.stream()
                                              .map(Question::getQuestionItem)
                                              .map(QuestionItem::getId)
                                              .collect(Collectors.toList());

        return queryFactory.select(answer.id.max())
                           .from(answer)
                           .innerJoin(answer.question, question)
                           .innerJoin(question.questionItem, questionItem)
                           .where(answer.account.id.eq(accountId),
                                  answer.project.id.in(projectIds),
                                  answer.question.questionItem.id.in(questionItemIds))
                           .groupBy(answer.project, answer.question.questionItem)
                           .fetch();

    }

    public List<Answer> findAllByArchives(List<Archive> archives) {
        List<Long> archiveIds = archives.stream()
                                        .map(Archive::getId)
                                        .collect(Collectors.toList());

        return queryFactory.selectFrom(answer)
                           .innerJoin(answer.project, project).fetchJoin()
                           .innerJoin(answer.archive, archive).fetchJoin()
                           .where(answer.archive.id.in(archiveIds))
                           .fetch();
    }
}
