package com.careerzip.domain.questionpaper.repository;

import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaperform.entity.QQuestionPaperForm;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.careerzip.domain.questionpaper.entity.QQuestionPaper.questionPaper;
import static com.careerzip.domain.questionpaperform.entity.QQuestionPaperForm.questionPaperForm;

@RequiredArgsConstructor
public class QuestionPaperRepositoryImpl implements QuestionPaperRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Optional<QuestionPaper> findLatest() {
        return Optional.ofNullable(queryFactory.selectFrom(questionPaper)
                                               .innerJoin(questionPaper.questionPaperForm, questionPaperForm).fetchJoin()
                                               .where(questionPaper.opened.eq(true))
                                               .orderBy(questionPaper.id.desc())
                                               .fetchFirst());

    }
}
