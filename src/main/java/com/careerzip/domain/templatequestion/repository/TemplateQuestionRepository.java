package com.careerzip.domain.templatequestion.repository;

import com.careerzip.domain.template.entity.Template;
import com.careerzip.domain.templatequestion.entity.TemplateQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateQuestionRepository extends JpaRepository<TemplateQuestion, Long>, TemplateQuestionRepositoryCustom {

    List<TemplateQuestion> findAllBy(Template template);
}
