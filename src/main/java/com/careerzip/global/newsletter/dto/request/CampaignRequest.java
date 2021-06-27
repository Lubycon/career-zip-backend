package com.careerzip.global.newsletter.dto.request;

import lombok.Getter;

@Getter
public class CampaignRequest {

    private final String campaignId;

    private CampaignRequest(String campaignId) {
        this.campaignId = campaignId;
    }

    public static CampaignRequest from(String campaignId) {
        return new CampaignRequest(campaignId);
    }
}
