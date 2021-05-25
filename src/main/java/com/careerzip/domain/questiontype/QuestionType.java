package com.careerzip.domain.questiontype;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class QuestionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_type_id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Builder
    private QuestionType(Long id, String description) {
        this.id = id;
        this.description = description;
    }
}
