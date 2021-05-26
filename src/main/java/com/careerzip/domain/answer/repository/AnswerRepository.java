package com.careerzip.domain.answer.repository;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.careerarchive.entity.CareerArchive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerRepositoryCustom {

    List<Answer> findAllBy(CareerArchive careerArchive, List<Long> letterFormQuestionIds);
}
