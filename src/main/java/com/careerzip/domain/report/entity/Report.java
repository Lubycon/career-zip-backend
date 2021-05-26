package com.careerzip.domain.report.entity;

import com.careerzip.global.jpa.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "shared")
    private Boolean shared;

    @Builder
    private Report(Long id, String title, Boolean shared) {
        this.id = id;
        this.title = title;
        this.shared = shared;
    }
}
