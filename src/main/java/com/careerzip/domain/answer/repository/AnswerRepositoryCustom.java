package com.careerzip.domain.answer.repository;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.question.entity.Question;

import java.util.List;

public interface AnswerRepositoryCustom {

    List<Answer> findAllBy(Archive archive, List<Question> questions);

    List<Answer> findAllBy(List<Long> answerIds);

    List<Answer> findAllByArchives(List<Archive> archives);

    List<Long> findAllPreviousIdsBy(Long accountId, List<Long> projectIds, List<Question> questions);
}
