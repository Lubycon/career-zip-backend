package com.careerzip.domain.question.repository;

import com.careerzip.domain.letterform.entity.LetterForm;
import com.careerzip.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {

    List<Question> findAllBy(LetterForm letterForm);
}
