package com.careerzip.global.newsletter.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OptInTypes {

    private final String email;
    private final String api;

    @JsonProperty("import")
    private final String importType;

    private final String webform;

    @Builder
    private OptInTypes(String email, String api, String importType, String webform) {
        this.email = email;
        this.api = api;
        this.importType = importType;
        this.webform = webform;
    }

    public static OptInTypes createSingleOptIn() {
        String SINGLE = "single";

        return OptInTypes.builder()
                         .email(SINGLE)
                         .api(SINGLE)
                         .importType(SINGLE)
                         .webform(SINGLE)
                         .build();
    }
}
