package com.careerzip.domain.record.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.record.dto.response.RecordSummary;
import com.careerzip.domain.record.dto.response.RecordsResponse;
import com.careerzip.domain.record.entity.Record;
import com.careerzip.domain.record.repository.RecordRepository;
import com.careerzip.global.error.exception.entity.AccountNotFoundException;
import com.careerzip.global.pagination.CustomPageRequest;
import com.careerzip.global.pagination.Pagination;
import com.careerzip.security.oauth.dto.OAuthAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RecordService {

    private final RecordRepository recordRepository;
    private final AccountRepository accountRepository;

    public RecordsResponse findAll(OAuthAccount loginAccount, Pagination pagination) {
        PageRequest pageRequest = CustomPageRequest.of(pagination);
        Account account = accountRepository.findById(loginAccount.getId()).orElseThrow(AccountNotFoundException::new);
        Page<Record> recordPage = recordRepository.findAllBy(account, pageRequest);
        List<RecordSummary> records = RecordSummary.listOf(recordPage);
        return RecordsResponse.of(recordPage, records);
    }
}
