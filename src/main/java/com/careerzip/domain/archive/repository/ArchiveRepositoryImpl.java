package com.careerzip.domain.archive.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archive.entity.Archive;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
}