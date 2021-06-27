package com.careerzip.global.admin.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.global.admin.dto.response.CampaignDetail;
import com.careerzip.global.admin.dto.response.CampaignsResponse;
import com.careerzip.global.admin.dto.response.ContactSummary;
import com.careerzip.global.newsletter.GetResponseClient;
import com.careerzip.global.newsletter.GetResponseProperties;
import com.careerzip.global.newsletter.JsonTranslator;
import com.careerzip.global.newsletter.dto.request.CampaignRequest;
import com.careerzip.global.newsletter.dto.request.ContactRequest;
import com.careerzip.global.newsletter.dto.response.Campaign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsLetterService {

    private final AccountRepository accountRepository;
    private final GetResponseClient getResponseClient;
    private final GetResponseProperties getResponseProperties;
    private final JsonTranslator jsonTranslator;

    public CampaignsResponse findAllCampaigns() {
        String json = getResponseClient.getRequest("/campaigns", String.class);
        List<Campaign> campaigns = jsonTranslator.mapToList(json, Campaign.class);
        List<CampaignDetail> campaignDetails = CampaignDetail.listOf(campaigns);
        return CampaignsResponse.from(campaignDetails);
    }

    public List<ContactSummary> addContactsToMainCampaign() {
        // 기본 조회 시작 시간은 오늘 날짜로부터 5일 전입니다.
        long days = 5L;
        LocalDateTime now = LocalDateTime.now();
        List<Account> accounts = accountRepository.findAllBy(now.minusDays(days), now);
        CampaignRequest campaign = CampaignRequest.from(getResponseProperties.getMainCampaignId());
        List<ContactRequest> requests = ContactRequest.listOf(campaign, accounts);
        requests.forEach(request -> getResponseClient.postRequest("/contacts", request, Void.class));
        return ContactSummary.listOf(accounts);
    }
}
