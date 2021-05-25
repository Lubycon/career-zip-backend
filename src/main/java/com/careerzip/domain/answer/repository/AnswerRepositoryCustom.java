package com.careerzip.domain.answer.repository;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.archiving.entity.Archiving;

import java.util.List;

public interface AnswerRepositoryCustom {

    List<Answer> findAllBy(Archiving archiving, List<Long> letterFormQuestionIds);
}
