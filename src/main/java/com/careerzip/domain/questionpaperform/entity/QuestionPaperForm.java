package com.careerzip.domain.questionpaperform.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class QuestionPaperForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_paper_form_id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Builder
    private QuestionPaperForm(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static QuestionPaperForm from(String title) {
        return QuestionPaperForm.builder()
                         .title(title)
                         .build();
    }
}
