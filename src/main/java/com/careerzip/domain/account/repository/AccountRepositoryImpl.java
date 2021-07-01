package com.careerzip.domain.account.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.entity.Provider;
import com.careerzip.domain.account.entity.QAccount;
import com.careerzip.domain.archive.entity.QArchive;
import com.careerzip.domain.questionpaper.entity.QQuestionPaper;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.careerzip.domain.account.entity.QAccount.account;
import static com.careerzip.domain.archive.entity.QArchive.archive;
import static com.careerzip.domain.questionpaper.entity.QQuestionPaper.questionPaper;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Account> findByOAuth(Provider provider, String oAuthId) {
        return Optional.ofNullable(queryFactory.selectFrom(account)
                                               .where(account.provider.eq(provider),
                                                      account.oAuthId.eq(oAuthId))
                                               .fetchOne());
    }

    @Override
    public List<Account> findAllBy(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return queryFactory.selectFrom(account)
                           .where(account.createdDateTime.between(startDateTime, endDateTime))
                           .fetch();
    }

    @Override
    public List<Account> findAllNotArchivedBy(QuestionPaper questionPaper) {
        List<Long> archivedIds = findAllArchivedIds(questionPaper);

        return queryFactory.selectFrom(account)
                           .where(account.id.notIn(archivedIds))
                           .fetch();
    }

    private List<Long> findAllArchivedIds(QuestionPaper questionPaper) {
        return queryFactory.select(account.id).from(account)
                           .innerJoin(archive).on(archive.account.eq(account))
                           .where(archive.questionPaper.eq(questionPaper))
                           .fetch();
    }
}
