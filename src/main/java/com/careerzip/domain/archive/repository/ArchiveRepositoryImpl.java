package com.careerzip.domain.archive.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archive.entity.Archive;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<Archive> findBy(Account account, Long archiveId) {
        return Optional.ofNullable(queryFactory.selectFrom(archive)
                                               .innerJoin(archive.questionPaper, questionPaper).fetchJoin()
                                               .innerJoin(questionPaper.questionPaperForm, questionPaperForm).fetchJoin()
                                               .where(archive.account.eq(account), archive.id.eq(archiveId))
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
