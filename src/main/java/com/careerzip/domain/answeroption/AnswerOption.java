package com.careerzip.domain.answeroption;

import com.careerzip.domain.questiontemplate.entity.QuestionTemplate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class AnswerOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_option_id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_template_id", nullable = false)
    private QuestionTemplate questionTemplate;

    @Builder
    private AnswerOption(Long id, String description, QuestionTemplate questionTemplate) {
        this.id = id;
        this.description = description;
        this.questionTemplate = questionTemplate;
    }
}
