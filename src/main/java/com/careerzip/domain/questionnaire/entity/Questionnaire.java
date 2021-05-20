package com.careerzip.domain.questionnaire.entity;

import com.careerzip.domain.template.entity.Template;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class Questionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questionnaire_id", nullable = false)
    private Long id;

    @Column(name = "questionnaire_title")
    private String title;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    @Builder
    private Questionnaire(Long id, String title, LocalDateTime startDateTime, LocalDateTime endDateTime, Template template) {
        this.id = id;
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.template = template;
    }
}