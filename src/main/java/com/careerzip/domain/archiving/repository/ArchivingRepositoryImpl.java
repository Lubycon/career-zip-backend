package com.careerzip.domain.archiving.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archiving.entity.Archiving;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.careerzip.domain.letter.entity.QQuestionnaire.questionnaire;
import static com.careerzip.domain.archiving.entity.QRecord.record;
import static com.careerzip.domain.letterform.entity.QTemplate.template;

@RequiredArgsConstructor
public class ArchivingRepositoryImpl implements ArchivingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<Archiving> findAllBy(Account account, Pageable pageable) {
        QueryResults<Archiving> results =
                queryFactory.selectFrom(record)
                            .innerJoin(record.questionnaire, questionnaire).fetchJoin()
                            .innerJoin(questionnaire.template, template).fetchJoin()
                            .where(record.account.eq(account))
                            .offset(pageable.getOffset())
                            .limit(pageable.getPageSize())
                            .fetchResults();
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public Optional<Archiving> findBy(Account account, Long recordId) {
        return Optional.ofNullable(queryFactory.selectFrom(record)
                                               .innerJoin(record.questionnaire, questionnaire).fetchJoin()
                                               .innerJoin(questionnaire.template, template).fetchJoin()
                                               .where(record.account.eq(account), record.id.eq(recordId))
                                               .fetchOne());
    }
}
