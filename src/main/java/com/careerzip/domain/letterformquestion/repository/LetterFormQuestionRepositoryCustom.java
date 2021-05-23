package com.careerzip.domain.letterformquestion.repository;

import com.careerzip.domain.letterform.entity.LetterForm;
import com.careerzip.domain.letterformquestion.entity.LetterFormQuestion;

import java.util.List;

public interface LetterFormQuestionRepositoryCustom {

    List<LetterFormQuestion> findAllBy(LetterForm letterForm);
}
