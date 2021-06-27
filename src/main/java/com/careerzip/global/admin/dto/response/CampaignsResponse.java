package com.careerzip.global.admin.dto.response;

import com.careerzip.global.newsletter.dto.response.Campaign;
import lombok.Getter;

import java.util.List;

@Getter
public class CampaignsResponse {

    List<CampaignDetail> campaigns;

    private CampaignsResponse(List<CampaignDetail> campaigns) {
        this.campaigns = campaigns;
    }

    public static CampaignsResponse from(List<CampaignDetail> campagins) {
        return new CampaignsResponse(campagins);
    }
}
