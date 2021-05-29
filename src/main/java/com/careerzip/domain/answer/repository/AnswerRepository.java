package com.careerzip.domain.answer.repository;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.archive.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerRepositoryCustom {

    List<Answer> findAllBy(Archive archive, List<Long> letterFormQuestionIds);

    List<Long> findAllPreviousIdsBy(Long accountId, List<Long> projectIds, List<Long> questionIds);
}
