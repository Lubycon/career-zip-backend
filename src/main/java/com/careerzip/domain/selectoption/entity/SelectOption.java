package com.careerzip.domain.selectoption.entity;

import com.careerzip.domain.questionitem.entity.QuestionItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class SelectOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "select_option_id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_item_id", nullable = false)
    private QuestionItem questionItem;

    @Builder
    private SelectOption(Long id, String description, QuestionItem questionItem) {
        this.id = id;
        this.description = description;
        this.questionItem = questionItem;
    }
}
