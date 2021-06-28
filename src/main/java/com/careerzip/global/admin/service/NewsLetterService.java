package com.careerzip.global.admin.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaper.repository.QuestionPaperRepository;
import com.careerzip.global.admin.dto.response.CampaignDetail;
import com.careerzip.global.admin.dto.response.CampaignsResponse;
import com.careerzip.global.admin.dto.response.ContactSummary;
import com.careerzip.global.admin.dto.response.NotArchivedContactsResponse;
import com.careerzip.global.error.exception.entity.QuestionPaperNotFoundException;
import com.careerzip.global.newsletter.GetResponseClient;
import com.careerzip.global.newsletter.GetResponseProperties;
import com.careerzip.global.newsletter.JsonTranslator;
import com.careerzip.global.newsletter.dto.request.CampaignRequest;
import com.careerzip.global.newsletter.dto.request.ContactRequest;
import com.careerzip.global.newsletter.dto.request.CreateCampaignRequest;
import com.careerzip.global.newsletter.dto.response.Campaign;
import com.careerzip.global.newsletter.dto.response.NewCampaign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NewsLetterService {

    private final AccountRepository accountRepository;
    private final QuestionPaperRepository questionPaperRepository;
    private final GetResponseClient getResponseClient;
    private final GetResponseProperties getResponseProperties;
    private final JsonTranslator jsonTranslator;

    public CampaignsResponse findAllCampaigns() {
        String json = getResponseClient.getRequest("/campaigns", String.class);
        List<Campaign> campaigns = jsonTranslator.mapToList(json, Campaign.class);
        List<CampaignDetail> campaignDetails = CampaignDetail.listOf(campaigns);
        return CampaignsResponse.from(campaignDetails);
    }

    public void addToMainCampaignBy(Account account) {
        try {
            CampaignRequest campaign = CampaignRequest.from(getResponseProperties.getMainCampaignId());
            ContactRequest request = ContactRequest.of(campaign, account);
            getResponseClient.postRequest("/contacts", request, Void.class);
        } catch (HttpClientErrorException exception) {
            log.error("handleGetResponseError - Email : {}", account.getEmail());
        }
    }

    public List<ContactSummary> addContactsToMainCampaign() {
        // 기본 조회 시작 시간은 오늘 날짜로부터 8일 전입니다.
        long days = 8L;
        LocalDateTime now = LocalDateTime.now();
        List<Account> accounts = accountRepository.findAllBy(now.minusDays(days), now);
        CampaignRequest campaign = CampaignRequest.from(getResponseProperties.getMainCampaignId());
        List<ContactRequest> requests = ContactRequest.listOf(campaign, accounts);
        requests.forEach(request -> getResponseClient.postRequest("/contacts", request, Void.class));
        return ContactSummary.listOf(accounts);
    }

    public void addRemindersCampaign() {
        QuestionPaper questionPaper = questionPaperRepository.findLatest().orElseThrow(QuestionPaperNotFoundException::new);
        CreateCampaignRequest createRequest = CreateCampaignRequest.from(questionPaper);
        NewCampaign createdCampaign = getResponseClient.postRequest("/campaigns", createRequest, NewCampaign.class);
        CampaignRequest campaign = CampaignRequest.from(createdCampaign.getCampaignId());
        List<Account> accounts = accountRepository.findAllNotArchivedBy(questionPaper);

        List<ContactRequest> requests = ContactRequest.listOf(campaign, accounts);
        Iterator<ContactRequest> iterator = requests.iterator();

        while (iterator.hasNext()) {
            try {
                ContactRequest request = iterator.next();
                getResponseClient.postRequest("/contacts", request, Void.class);
            } catch (HttpClientErrorException exception) {
                exception.printStackTrace();
                log.error("Contact Insert Error Email");
            }
        }
    }

    public NotArchivedContactsResponse findAllNotArchivedContacts() {
        QuestionPaper questionPaper = questionPaperRepository.findLatest().orElseThrow(QuestionPaperNotFoundException::new);
        List<Account> accounts = accountRepository.findAllNotArchivedBy(questionPaper);
        List<ContactSummary> contacts = ContactSummary.listOf(accounts);
        return NotArchivedContactsResponse.of(questionPaper, contacts);
    }
}
