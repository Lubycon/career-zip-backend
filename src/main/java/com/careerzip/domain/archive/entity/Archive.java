package com.careerzip.domain.archive.entity;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.global.jpa.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Archive extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archive_id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_paper_id", nullable = false)
    private QuestionPaper questionPaper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Builder
    private Archive(Long id, String title, QuestionPaper questionPaper, Account account) {
        this.id = id;
        this.title = title;
        this.questionPaper = questionPaper;
        this.account = account;
    }

    public static Archive of(QuestionPaper questionPaper, Account account) {
        return Archive.builder()
                      .questionPaper(questionPaper)
                      .account(account)
                      .build();
    }
}
