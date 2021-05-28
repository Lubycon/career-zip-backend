package com.careerzip.domain.question.repository;

import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import com.careerzip.domain.question.entity.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.careerzip.domain.question.entity.QQuestion.question;
import static com.careerzip.domain.questionitem.entity.QQuestionItem.questionItem;
import static com.careerzip.domain.questiontype.entity.QQuestionType.questionType;
import static com.careerzip.domain.selectoption.entity.QSelectOption.selectOption;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Question> findAllBy(QuestionPaperForm questionPaperForm) {
        return queryFactory.selectFrom(question)
                           .innerJoin(question.questionItem, questionItem).fetchJoin()
                           .innerJoin(questionItem.questionType, questionType).fetchJoin()
                           .leftJoin(questionItem.selectOptions, selectOption).fetchJoin()
                           .where(question.questionPaperForm.eq(questionPaperForm))
                           .distinct()
                           .fetch();
    }
}
