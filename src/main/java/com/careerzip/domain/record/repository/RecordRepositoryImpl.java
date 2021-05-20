package com.careerzip.domain.record.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.record.entity.Record;
import com.careerzip.domain.template.entity.QTemplate;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.careerzip.domain.questionnaire.entity.QQuestionnaire.questionnaire;
import static com.careerzip.domain.record.entity.QRecord.record;
import static com.careerzip.domain.template.entity.QTemplate.template;

@RequiredArgsConstructor
public class RecordRepositoryImpl implements RecordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<Record> findAllBy(Account account, Pageable pageable) {
        QueryResults<Record> results =
                queryFactory.selectFrom(record)
                            .innerJoin(record.questionnaire, questionnaire).fetchJoin()
                            .innerJoin(questionnaire.template, template).fetchJoin()
                            .where(record.account.eq(account))
                            .offset(pageable.getOffset())
                            .limit(pageable.getPageSize())
                            .fetchResults();
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public Optional<Record> findBy(Account account, Long recordId) {
        return Optional.ofNullable(queryFactory.selectFrom(record)
                                               .innerJoin(record.questionnaire, questionnaire).fetchJoin()
                                               .innerJoin(questionnaire.template, template).fetchJoin()
                                               .where(record.account.eq(account), record.id.eq(recordId))
                                               .fetchOne());
    }
}
