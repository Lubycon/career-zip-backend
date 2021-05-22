package com.careerzip.domain.question.entity;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.questiontemplate.entity.QuestionTemplate;
import com.careerzip.domain.template.entity.Template;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_template_id", nullable = false)
    private QuestionTemplate questionTemplate;

    @Column(name = "priority")
    private Integer priority;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<Answer> answers = new ArrayList<>();

    @Builder
    private Question(Long id, Template template, QuestionTemplate questionTemplate, Integer priority) {
        this.id = id;
        this.template = template;
        this.questionTemplate = questionTemplate;
        this.priority = priority;
    }
}
