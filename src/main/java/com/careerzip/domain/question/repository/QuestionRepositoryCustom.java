package com.careerzip.domain.question.repository;

import com.careerzip.domain.template.entity.Template;
import com.careerzip.domain.question.entity.Question;

import java.util.List;

public interface QuestionRepositoryCustom {

    List<Question> findAllBy(Template template);
}
