package com.careerzip.domain.archive.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.entity.QAccount;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.questionpaper.entity.QQuestionPaper;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.careerzip.domain.account.entity.QAccount.account;
import static com.careerzip.domain.archive.entity.QArchive.archive;
import static com.careerzip.domain.questionpaper.entity.QQuestionPaper.questionPaper;
import static com.careerzip.domain.questionpaperform.entity.QQuestionPaperForm.questionPaperForm;

@RequiredArgsConstructor
public class ArchiveRepositoryImpl implements ArchiveRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<Archive> findAllBy(Account account, Pageable pageable) {
        QueryResults<Archive> results =
                queryFactory.selectFrom(archive)
                            .innerJoin(archive.questionPaper, questionPaper).fetchJoin()
                            .innerJoin(questionPaper.questionPaperForm, questionPaperForm).fetchJoin()
                            .where(archive.account.eq(account))
                            .orderBy(specifyOrder(pageable.getSort())
                                                          .toArray(OrderSpecifier[]::new))
                            .offset(pageable.getOffset())
                            .limit(pageable.getPageSize())
                            .fetchResults();
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public Page<Archive> findAllBy(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        // startTime - 아카이빙 시작 시간은 자정 입니다.
        // endTime - 아카이빙 끝 시간은 23분 59분 59초 입니다.
        LocalTime startTime = LocalTime.of(0, 0, 0);
        LocalTime endTime = LocalTime.of(23, 59, 59);

        QueryResults<Archive> results =
                queryFactory.selectFrom(archive)
                            .innerJoin(archive.questionPaper, questionPaper).fetchJoin()
                            .innerJoin(archive.account, account).fetchJoin()
                            .where(archive.createdDateTime.between(LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime)))
                            .orderBy(specifyOrder(pageable.getSort())
                                                          .toArray(OrderSpecifier[]::new))
                            .offset(pageable.getOffset())
                            .limit(pageable.getPageSize())
                            .fetchResults();
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public Optional<Archive> findBy(Long archiveId) {
        return Optional.ofNullable(queryFactory.selectFrom(archive)
                       .innerJoin(archive.questionPaper, questionPaper).fetchJoin()
                       .innerJoin(questionPaper.questionPaperForm, questionPaperForm).fetchJoin()
                       .where(archive.id.eq(archiveId))
                       .fetchOne());
    }

    public Optional<Archive> findBy(Account account, Long archiveId) {
        return Optional.ofNullable(queryFactory.selectFrom(archive)
                                               .innerJoin(archive.questionPaper, questionPaper).fetchJoin()
                                               .innerJoin(questionPaper.questionPaperForm, questionPaperForm).fetchJoin()
                                               .where(archive.account.eq(account), archive.id.eq(archiveId))
                                               .fetchOne());
    }

    public Optional<Archive> findBy(Account account, QuestionPaper questionPaper) {
        return Optional.ofNullable(queryFactory.selectFrom(archive)
                                               .innerJoin(archive.questionPaper, QQuestionPaper.questionPaper)
                                               .where(archive.account.eq(account), archive.questionPaper.eq(questionPaper))
                                               .orderBy(archive.id.desc())
                                               .limit(1L)
                                               .fetchOne());
    }

    private List<OrderSpecifier> specifyOrder(Sort sort) {
        String archive = "archive";
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Archive.class, archive);
            orders.add(new OrderSpecifier(direction, orderByExpression.get(property)));
        });

        return orders;
    }
}
