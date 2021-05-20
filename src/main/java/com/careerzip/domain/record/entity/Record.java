package com.careerzip.domain.record.entity;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.questionnaire.entity.Questionnaire;
import com.careerzip.global.jpa.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Record extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_id", nullable = false)
    private Questionnaire questionnaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Builder
    private Record(Long id, String title, Questionnaire questionnaire, Account account) {
        this.id = id;
        this.title = title;
        this.questionnaire = questionnaire;
        this.account = account;
    }
}
