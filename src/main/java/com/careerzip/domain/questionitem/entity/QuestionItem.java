package com.careerzip.domain.questionitem.entity;

import com.careerzip.domain.selectoption.entity.SelectOption;
import com.careerzip.domain.questiontype.QuestionType;
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
public class QuestionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_item_id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "example", nullable = false)
    private String example;

    @Enumerated(EnumType.STRING)
    @Column(name = "input_type", nullable = false)
    private InputType inputType;

    @ManyToOne
    @JoinColumn(name = "question_type_id", nullable = false)
    private QuestionType questionType;

    @OneToMany(mappedBy = "questionItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SelectOption> selectOptions = new ArrayList<>();

    @Builder
    private QuestionItem(Long id, String description, String example, InputType inputType, QuestionType questionType) {
        this.id = id;
        this.description = description;
        this.example = example;
        this.inputType = inputType;
        this.questionType = questionType;
    }
}
