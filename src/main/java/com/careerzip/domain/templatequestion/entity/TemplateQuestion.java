package com.careerzip.domain.templatequestion.entity;

import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.template.entity.Template;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class TemplateQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_question_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "order")
    private Integer order;

    @Builder
    private TemplateQuestion(Long id, Template template, Question question, Integer order) {
        this.id = id;
        this.template = template;
        this.question = question;
        this.order = order;
    }
}
