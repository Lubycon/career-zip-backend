package com.careerzip.domain.answer.entity;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.hashtag.entity.Hashtag;
import com.careerzip.domain.questiontemplate.entity.QuestionTemplate;
import com.careerzip.domain.record.entity.Record;
import com.careerzip.domain.question.entity.Question;
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
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_template_id", nullable = false)
    private QuestionTemplate questionTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Builder
    private Answer(Long id, String comment, Hashtag hashtag, QuestionTemplate questionTemplate, Record record, Account account) {
        this.id = id;
        this.comment = comment;
        this.hashtag = hashtag;
        this.questionTemplate = questionTemplate;
        this.record = record;
        this.account = account;
    }
}
