package com.careerzip.domain.account.service;

import com.careerzip.domain.account.dto.request.AccountUpdateRequest;
import com.careerzip.domain.account.dto.response.AccountDetail;
import com.careerzip.domain.account.dto.response.AccountSummary;
import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.job.entity.Job;
import com.careerzip.global.error.exception.business.AccountMismatchException;
import com.careerzip.global.error.exception.entity.AccountNotFoundException;
import com.careerzip.global.jwt.JwtTokenProvider;
import com.careerzip.security.oauth.dto.OAuthAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String issueJwtToken(String authorizationHeader) {
        Long accountId = jwtTokenProvider.parsePreAuthToken(authorizationHeader);
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        return jwtTokenProvider.issueJwtToken(account);
    }

    public AccountDetail findBy(String authorizationHeader) {
        Long accountId = jwtTokenProvider.parsePreAuthToken(authorizationHeader);
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        return AccountDetail.from(account);
    }

    @Transactional
    public AccountSummary update(OAuthAccount loginAccount, Long accountId, AccountUpdateRequest updateRequest) {
        Account account = accountRepository.findById(loginAccount.getId()).orElseThrow();

        if (account.isDifferentAccount(accountId)) {
            throw new AccountMismatchException();
        }

        account.update(updateRequest.getName(), updateRequest.getEmail());
        return AccountSummary.from(account);
    }
}
