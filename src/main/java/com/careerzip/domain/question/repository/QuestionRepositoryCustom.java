package com.careerzip.domain.question.repository;

import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import com.careerzip.domain.question.entity.Question;

import java.util.List;

public interface QuestionRepositoryCustom {

    List<Question> findAllBy(QuestionPaperForm questionPaperForm);

    List<Question> findAllByIds(List<Long> questionIds);
}
