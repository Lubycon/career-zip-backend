package com.careerzip.domain.letterform.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class LetterForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_form_id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Builder
    private LetterForm(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static LetterForm from(String title) {
        return LetterForm.builder()
                         .title(title)
                         .build();
    }
}
