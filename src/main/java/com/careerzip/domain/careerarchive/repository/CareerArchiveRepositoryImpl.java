package com.careerzip.domain.careerarchive.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.careerarchive.entity.CareerArchive;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.careerzip.domain.careerarchive.entity.QArchiving.archiving;
import static com.careerzip.domain.questionpaper.entity.QLetter.letter;
import static com.careerzip.domain.questionpaperform.entity.QLetterForm.letterForm;

@RequiredArgsConstructor
public class CareerArchiveRepositoryImpl implements CareerArchiveRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<CareerArchive> findAllBy(Account account, Pageable pageable) {
        QueryResults<CareerArchive> results =
                queryFactory.selectFrom(archiving)
//                            .innerJoin(archiving.letter, letter).fetchJoin()
                            .innerJoin(letter.letterForm, letterForm).fetchJoin()
                            .where(archiving.account.eq(account))
                            .offset(pageable.getOffset())
                            .limit(pageable.getPageSize())
                            .fetchResults();
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public Optional<CareerArchive> findBy(Account account, Long archivingId) {
        return Optional.ofNullable(queryFactory.selectFrom(archiving)
//                                               .innerJoin(archiving.letter, letter).fetchJoin()
                                               .innerJoin(letter.letterForm, letterForm).fetchJoin()
                                               .where(archiving.account.eq(account), archiving.id.eq(archivingId))
                                               .fetchOne());
    }
}
