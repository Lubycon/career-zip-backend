package com.careerzip.global.admin.service;

import com.careerzip.global.admin.dto.response.CampaignDetail;
import com.careerzip.global.admin.dto.response.CampaignsResponse;
import com.careerzip.global.newsletter.GetResponseClient;
import com.careerzip.global.newsletter.JsonTranslator;
import com.careerzip.global.newsletter.dto.response.Campaign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsLetterService {

    private final GetResponseClient getResponseClient;
    private final JsonTranslator jsonTranslator;

    public CampaignsResponse findAllCampaigns() {
        String json = getResponseClient.requestGetResponse("/campaigns", HttpMethod.GET, String.class);
        List<Campaign> campaigns = jsonTranslator.mapToList(json, Campaign.class);
        List<CampaignDetail> campaignDetails = CampaignDetail.listOf(campaigns);
        return CampaignsResponse.from(campaignDetails);
    }
}
