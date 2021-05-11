package com.careerzip.domain.account.service;

import com.careerzip.domain.account.dto.request.AccountRequest;
import com.careerzip.domain.account.dto.response.AccountSummary;
import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.entity.Provider;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.global.error.exception.auth.InvalidOAuthProviderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public AccountSummary find(AccountRequest accountRequest) {
        if (Provider.isInvalidProvider(accountRequest.getProvider())) {
            throw new InvalidOAuthProviderException();
        }

        Account account = accountRepository.findByOAuth(Provider.valueOf(accountRequest.getProvider()), accountRequest.getOAuthId())
                                           .orElseGet(() -> {
                                               Account newAccount = accountRequest.toEntity();
                                               return accountRepository.save(newAccount);
                                           });

        return AccountSummary.from(account);
    }
}
