package com.careerzip.global.newsletter.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Campaign {

    private String description;
    private String campaignId;
    private String name;
    private String createdOn;
}
