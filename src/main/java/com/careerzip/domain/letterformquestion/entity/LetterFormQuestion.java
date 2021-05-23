package com.careerzip.domain.letterformquestion.entity;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.letterform.entity.LetterForm;
import com.careerzip.domain.question.entity.Question;
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
public class LetterFormQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_form_question_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_form_id", nullable = false)
    private LetterForm letterForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "priority")
    private Integer priority;

    @OneToMany(mappedBy = "letterFormQuestion", fetch = FetchType.LAZY)
    private List<Answer> answers = new ArrayList<>();

    @Builder
    private LetterFormQuestion(Long id, LetterForm letterForm, Question question, Integer priority) {
        this.id = id;
        this.letterForm = letterForm;
        this.question = question;
        this.priority = priority;
    }
}
