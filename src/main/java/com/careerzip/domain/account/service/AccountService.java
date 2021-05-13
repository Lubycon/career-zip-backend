package com.careerzip.domain.account.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.global.error.exception.entity.AccountNotFoundException;
import com.careerzip.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String issueJwtToken(String authorizationHeader) {
        Long accountId = jwtTokenProvider.parsePreAuthToken(authorizationHeader);
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        return jwtTokenProvider.issueJwtToken(account);
    }
}
