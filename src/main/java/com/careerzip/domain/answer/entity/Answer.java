package com.careerzip.domain.answer.entity;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.letterformquestion.entity.LetterFormQuestion;
import com.careerzip.domain.project.entity.Project;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id", nullable = false)
    private Long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_form_question_id", nullable = false)
    private LetterFormQuestion letterFormQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archiving_id", nullable = false)
    private Archiving archiving;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Builder
    private Answer(Long id, String comment, Project project, LetterFormQuestion letterFormQuestion, Archiving archiving, Account account) {
        this.id = id;
        this.comment = comment;
        this.project = project;
        this.letterFormQuestion = letterFormQuestion;
        this.archiving = archiving;
        this.account = account;
    }
}
