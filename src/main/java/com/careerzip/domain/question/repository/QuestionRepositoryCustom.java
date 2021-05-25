package com.careerzip.domain.question.repository;

import com.careerzip.domain.letterform.entity.LetterForm;
import com.careerzip.domain.question.entity.Question;

import java.util.List;

public interface QuestionRepositoryCustom {

    List<Question> findAllBy(LetterForm letterForm);
}
