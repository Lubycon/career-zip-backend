package com.careerzip.global.newsletter.dto.request;

import com.careerzip.domain.account.entity.Account;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ContactRequest {

    private final CampaignRequest campaign;
    private final String name;
    private final String email;

    @Builder
    private ContactRequest(CampaignRequest campaign, String name, String email) {
        this.campaign = campaign;
        this.name = name;
        this.email = email;
    }

    public static ContactRequest from(Account account) {
        return ContactRequest.builder()
                             .campaign(CampaignRequest.from("zKaDh"))
                             .name(account.getName())
                             .email(account.getEmail())
                             .build();
    }

    public static List<ContactRequest> listOf(List<Account> accounts) {
        return accounts.stream()
                       .map(ContactRequest::from)
                       .collect(Collectors.toList());
    }
}
