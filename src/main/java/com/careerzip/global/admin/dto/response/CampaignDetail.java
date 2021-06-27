package com.careerzip.global.admin.dto.response;

import com.careerzip.global.newsletter.dto.response.Campaign;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CampaignDetail {

    private String description;
    private String campaignId;
    private String name;

    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime createdDateTime;

    @Builder
    private CampaignDetail(String description, String campaignId, String name, LocalDateTime createdDateTime) {
        this.description = description;
        this.campaignId = campaignId;
        this.name = name;
        this.createdDateTime = createdDateTime;
    }

    public static CampaignDetail from(Campaign campaign) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        LocalDateTime createdDateTime = LocalDateTime.parse(campaign.getCreatedOn(), formatter);

        return CampaignDetail.builder()
                             .description(campaign.getDescription())
                             .campaignId(campaign.getCampaignId())
                             .name(campaign.getName())
                             .createdDateTime(createdDateTime)
                             .build();
    }

    public static List<CampaignDetail> listOf(List<Campaign> campaigns) {
        return campaigns.stream()
                        .map(CampaignDetail::from)
                        .collect(Collectors.toList());
    }
}
