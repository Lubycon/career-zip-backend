package com.careerzip.domain.templatequestion.repository;

import com.careerzip.domain.template.entity.Template;
import com.careerzip.domain.templatequestion.entity.TemplateQuestion;

import java.util.List;

public interface TemplateQuestionRepositoryCustom {

    List<TemplateQuestion> findAllBy(Template template);
}
