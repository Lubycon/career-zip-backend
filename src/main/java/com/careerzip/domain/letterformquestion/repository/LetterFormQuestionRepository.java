package com.careerzip.domain.letterformquestion.repository;

import com.careerzip.domain.letterformquestion.entity.LetterFormQuestion;
import com.careerzip.domain.letterform.entity.LetterForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterFormQuestionRepository extends JpaRepository<LetterFormQuestion, Long>, LetterFormQuestionRepositoryCustom {

    List<LetterFormQuestion> findAllBy(LetterForm letterForm);
}
