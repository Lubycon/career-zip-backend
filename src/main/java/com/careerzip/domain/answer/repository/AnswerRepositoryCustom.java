package com.careerzip.domain.answer.repository;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.careerarchive.entity.CareerArchive;

import java.util.List;

public interface AnswerRepositoryCustom {

    List<Answer> findAllBy(CareerArchive careerArchive, List<Long> letterFormQuestionIds);
}
