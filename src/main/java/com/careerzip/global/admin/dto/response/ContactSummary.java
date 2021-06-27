package com.careerzip.global.admin.dto.response;

import com.careerzip.domain.account.entity.Account;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ContactSummary {

    private final String name;
    private final String email;

    @Builder
    private ContactSummary(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static ContactSummary from(Account account) {
        return ContactSummary.builder()
                             .name(account.getName())
                             .email(account.getEmail())
                             .build();
    }

    public static List<ContactSummary> listOf(List<Account> accounts) {
        return accounts.stream()
                        .map(ContactSummary::from)
                        .collect(Collectors.toList());
    }
}
