package com.careerzip.domain.letter.entity;

import com.careerzip.domain.letterform.entity.LetterForm;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Letter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_form_id", nullable = false)
    private LetterForm letterForm;

    @Builder
    private Letter(Long id, String title, LocalDateTime startDateTime, LocalDateTime endDateTime, LetterForm letterForm) {
        this.id = id;
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.letterForm = letterForm;
    }
}
