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

import static com.careerzip.domain.archiving.entity.QArchiving.archiving;
import static com.careerzip.domain.questionpaper.entity.QLetter.letter;
import static com.careerzip.domain.questionpaperform.entity.QLetterForm.letterForm;

@RequiredArgsConstructor
public class ArchivingRepositoryImpl implements ArchivingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<Archiving> findAllBy(Account account, Pageable pageable) {
        QueryResults<Archiving> results =
                queryFactory.selectFrom(archiving)
//                            .innerJoin(archiving.letter, letter).fetchJoin()
                            .innerJoin(letter.letterForm, letterForm).fetchJoin()
                            .where(archiving.account.eq(account))
                            .offset(pageable.getOffset())
                            .limit(pageable.getPageSize())
                            .fetchResults();
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public Optional<Archiving> findBy(Account account, Long archivingId) {
        return Optional.ofNullable(queryFactory.selectFrom(archiving)
//                                               .innerJoin(archiving.letter, letter).fetchJoin()
                                               .innerJoin(letter.letterForm, letterForm).fetchJoin()
                                               .where(archiving.account.eq(account), archiving.id.eq(archivingId))
                                               .fetchOne());
    }
}
