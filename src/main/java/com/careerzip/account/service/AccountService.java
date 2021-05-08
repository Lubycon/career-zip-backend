package com.careerzip.account.service;

import com.careerzip.account.dto.request.AccountRequest;
import com.careerzip.account.dto.response.AccountSummary;
import com.careerzip.account.entity.Account;
import com.careerzip.account.entity.Provider;
import com.careerzip.account.repository.AccountRepository;
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
                                               Account newAccount = Account.from(accountRequest);
                                               return accountRepository.save(newAccount);
                                           });

        return AccountSummary.from(account);
    }
}
