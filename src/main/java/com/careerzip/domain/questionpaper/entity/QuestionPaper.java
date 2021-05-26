package com.careerzip.domain.questionpaper.entity;

import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class QuestionPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_paper_id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_paer_form_id", nullable = false)
    private QuestionPaperForm questionPaperForm;

    @Builder
    private QuestionPaper(Long id, String title, LocalDateTime startDateTime, LocalDateTime endDateTime, QuestionPaperForm questionPaperForm) {
        this.id = id;
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.questionPaperForm = questionPaperForm;
    }
}
