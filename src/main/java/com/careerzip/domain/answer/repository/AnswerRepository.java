package com.careerzip.domain.answer.repository;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.archiving.entity.Archiving;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerRepositoryCustom {

    List<Answer> findAllBy(Archiving archiving, List<Long> letterFormQuestionIds);
}
