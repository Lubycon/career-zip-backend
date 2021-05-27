package com.careerzip.domain.answer.repository;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.archive.entity.Archive;

import java.util.List;

public interface AnswerRepositoryCustom {

    List<Answer> findAllBy(Archive archive, List<Long> letterFormQuestionIds);
}
