package com.careerzip.domain.answer.entity;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.question.entity.Question;
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

    @Column(name = "important", nullable = false)
    private Boolean important;

    @Column(name = "shared", nullable = false)
    private Boolean shared;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archiving_id", nullable = false)
    private Archiving archiving;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Builder
    private Answer(Long id, String comment, Boolean important, Boolean shared, Project project, Question question, Archiving archiving, Account account) {
        this.id = id;
        this.comment = comment;
        this.important = important;
        this.shared = shared;
        this.project = project;
        this.question = question;
        this.archiving = archiving;
        this.account = account;
    }

    public Long getQuestionId() {
        return question.getId();
    }
}
