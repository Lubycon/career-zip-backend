package com.careerzip.domain.archiving.entity;

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
public class Archiving extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archiving_id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id", nullable = false)
    private QuestionPaper questionPaper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Builder
    private Archiving(Long id, String title, QuestionPaper questionPaper, Account account) {
        this.id = id;
        this.title = title;
        this.questionPaper = questionPaper;
        this.account = account;
    }
}
