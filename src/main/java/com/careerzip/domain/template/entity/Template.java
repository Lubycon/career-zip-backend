package com.careerzip.domain.template.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Builder
    private Template(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static Template from(String title) {
        return Template.builder()
                       .title(title)
                       .build();
    }
}
