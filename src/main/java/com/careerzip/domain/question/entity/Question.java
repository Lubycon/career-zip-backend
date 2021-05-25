package com.careerzip.domain.question.entity;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.letterform.entity.LetterForm;
import com.careerzip.domain.questionitem.entity.QuestionItem;
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
    @JoinColumn(name = "letter_form_id", nullable = false)
    private LetterForm letterForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_item_id", nullable = false)
    private QuestionItem questionItem;

    @Column(name = "priority")
    private Integer priority;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<Answer> answers = new ArrayList<>();

    @Builder
    private Question(Long id, LetterForm letterForm, QuestionItem questionItem, Integer priority) {
        this.id = id;
        this.letterForm = letterForm;
        this.questionItem = questionItem;
        this.priority = priority;
    }
}
