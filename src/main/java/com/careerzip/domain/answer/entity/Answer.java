package com.careerzip.domain.answer.entity;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.report.entity.Report;
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
    @JoinColumn(name = "archive_id", nullable = false)
    private Archive archive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

    @Builder
    private Answer(Long id, String comment, Boolean important, Boolean shared, Project project, Question question, Archive archive, Report report, Account account) {
        this.id = id;
        this.comment = comment;
        this.important = important;
        this.shared = shared;
        this.project = project;
        this.question = question;
        this.archive = archive;
        this.report = report;
        this.account = account;
    }

    public Long getQuestionId() {
        return question.getId();
    }

    public static Answer of(Project project, Question question, Archive archive, Account account, String comment) {
        return Answer.builder()
                     .comment(comment)
                     .important(false)
                     .shared(false)
                     .project(project)
                     .question(question)
                     .archive(archive)
                     .account(account)
                     .build();
    }
}
