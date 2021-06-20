package com.careerzip.domain.acquisition;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Acquisition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acquisition_id")
    private Long id;

    @Column(name = "utm_source")
    private String utmSource;

    @Builder
    private Acquisition(Long id, String utmSource) {
        this.id = id;
        this.utmSource = utmSource;
    }

    public static Acquisition from(String utmSource) {
        return Acquisition.builder()
                          .utmSource(utmSource)
                          .build();
    }
}
