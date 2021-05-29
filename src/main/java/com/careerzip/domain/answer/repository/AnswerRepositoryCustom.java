package com.careerzip.domain.answer.repository;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.archive.entity.Archive;

import java.util.List;

public interface AnswerRepositoryCustom {

    List<Answer> findAllBy(Archive archive, List<Long> letterFormQuestionIds);

    List<Answer> findAllBy(List<Long> answerIds);

    List<Answer> findAllByArchives(List<Archive> archives);

    List<Long> findAllPreviousIdsBy(Long accountId, List<Long> projectIds, List<Long> questionIds);
}
